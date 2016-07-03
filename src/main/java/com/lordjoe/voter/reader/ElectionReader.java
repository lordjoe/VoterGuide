package com.lordjoe.voter.reader;

import com.lordjoe.voter.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.text.*;
import java.util.Date;

/**
 * com.lordjoe.voter.reader.ElectionReader
 * User: Steve
 * Date: 6/25/2016
 */
public class ElectionReader {
    public static final String CANDIDATE_LIST = "article prepend-3 candidates-list";
    public static final String CANDIDATE_SUB1 = "border-top-1 clearfix";


    public static void readElections(File directory, Integer year) {
        File[] files = directory.listFiles();
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            processElectionDirectory(file, year);

        }
    }

    private static void processElectionDirectory(File file, Integer year) {
        String type = file.getName();
        if (type.equalsIgnoreCase("J"))
            return; // drop judicial
        if (type.equalsIgnoreCase("K"))
            return; // drop judicial
        if (type.equalsIgnoreCase("L"))
            return; // drop l;egislative
        if (type.equalsIgnoreCase("S"))
            return; // drop statewide
        File[] candidateFiles = file.listFiles();
        if (candidateFiles.length == 0)
            throw new IllegalStateException("bad CandidateFiles");

        File cf = candidateFiles[0];
        processElectionFile(type, year, cf);

    }

    private static void processElectionFile(String type, Integer year, File cf) {
        try {
            Document doc = Jsoup.parse(cf, "UTF-8");
            Element candidates = JSoupUtilities.getOneSubItemOfClass(doc, CANDIDATE_LIST);
            candidates = JSoupUtilities.getOneSubItemOfClass(candidates, "border-top-1 clearfix");
            candidates = JSoupUtilities.getOneSubItemOfClass(candidates, "append-2");

            processCandidates(type, year, candidates);
        } catch (IOException e) {
            throw new RuntimeException(e);

        }

    }

    private static void processCandidates(String type, Integer year, Element candidates) {
        Race r = null;
        for (Element element : candidates.children()) {
            if (element.tagName().equals("h4")) {
                r = buildRace(type, year, element);
                continue;
            }
            if (element.className().equals("row clearfix candidate-item")) {
                Candidate c = buildCandidate(r, element);
                if (c != null)
                    r.addCandidate(c);
                continue;
            }

        }
    }

    private static Candidate buildCandidate(Race r, Element element) {
        Party party = Party.Independent;
        Politician pol = null;
        Candidate c = null;
        Boolean incumbent = false;
        Element candidate = JSoupUtilities.getOneSubItemOfClass(element, "detailscontainer");
        String text = candidate.text();
        for (Element element1 : candidate.children()) {
            String tag = element1.tagName();
            if (tag.equals("h5")) {
                pol = politicianFromElement(element1);
            }
            if (tag.equals("strong")) {
                text = element1.text();
                if (text.startsWith("(Running)"))
                    continue;
                if (text.contains("Incumbent")) {
                    incumbent = true;
                    continue;
                }
                if (text.startsWith("(Withdrawn)"))
                    return null;
                if (text.startsWith("U.S. Senate")) {
                    if (text.contains("Incumbent"))
                        incumbent = true;

                    continue;
                }
                if (text.startsWith("U.S. House"))
                    continue;
                if (text.contains("Governor"))
                    continue;
                   if (text.startsWith("District"))
                    continue;
                Party px = partyFromText(text);
                if (px != null) {
                    party = px;
                    continue;
                }
            }
        }

        if (pol != null) {
            c = Candidates.getCandidate(pol, party, r);
        }
        return c;
    }

    private static Politician politicianFromElement(Element element) {
        Elements select = element.select("a");
        String candidateRef = select.first().attr("href");
        String idStr = candidateRef.replace("http://votesmart.org/candidate/", "");
        idStr = idStr.replace("/candidate/", "");
        String substring = idStr.substring(0, idStr.indexOf("/"));
        try {
            Integer id = Integer.parseInt(substring);
            Politician politician = Politicians.getByID(id);
            if (politician == null) {
                politician = CandidateReader.readCandidate(id);
            }


            return politician;
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);

        }
    }

    private static Party partyFromText(String text) {
        try {
            if (text.startsWith("At-Large"))
                return Party.At_Large;
            if (text.startsWith("Green Party"))
                return Party.Green_Party;
            if (text.startsWith("Democratic"))
                return Party.Democrat;
            if ("Write-In".equalsIgnoreCase(text))
                return Party.WriteIn;
            if ("No Party Affiliation".equalsIgnoreCase(text))
                return Party.Independent;
            Party p = Party.valueOf(text);
            return p;
        } catch (Exception e) {
            return null;

        }
    }

    private static Race buildRace(String type, Integer year, Element element) {

        OfficeLevel level = levelFromType(type);
        Date date = getElectionDate(element);
        String rName = element.id();
        String rText = element.text();
        OfficeType otype = officeFromRaceText(rText);
        State state = stateFromRaceText(rText);
          int index = rName.indexOf("-");
        if (index > -1) {
            rName = rName.substring(index + 1);
        }
        Integer number = null;
        try {
            number = Integer.parseInt(rName);
        } catch (NumberFormatException e) {
           }
        District district = Districts.getDistrict(otype, state, number);
        return Races.getRace(district, year);
    }

    public static OfficeType officeFromRaceText(String text) {
        if (text.contains("U.S. Senate"))
            return OfficeType.Senator;
        if (text.contains("U.S. House"))
            return OfficeType.Congressman;
        if (text.contains("Governor"))
            return OfficeType.Governor;
        throw new UnsupportedOperationException("Unknown office " + text);
    }

    public static State stateFromRaceText(String text) {
        String[] split = text.split(" ");
        String name = split[0];
        if (name.equalsIgnoreCase("NORTH"))
            name = name + "_" + split[1];
        if (name.equalsIgnoreCase("SOUTH"))
            name = name + "_" + split[1];
        if (name.equalsIgnoreCase("WEST"))
            name = name + "_" + split[1];
        if (name.equalsIgnoreCase("NEW"))
            name = name + "_" + split[1];

        return State.valueOf(name.toUpperCase());

    }

    public static final DateFormat DF = new SimpleDateFormat("MMM. d, yyyy");

    private static Date getElectionDate(Element element) {

        try {
            Element electiondate = JSoupUtilities.getOneSubItemOfClass(element, "span", "electiondate");
            String dataName = electiondate.text().replace("(", "").replace(")", "");
            Date parse = DF.parse(dataName);
            return parse;
        } catch (ParseException e) {
            throw new RuntimeException(e);

        }
    }

    private static OfficeLevel levelFromType(String type) {
        switch (type.charAt(0)) {
            case 'C':
                return OfficeLevel.Federal;
            case 'L':
            case 'S':
            case 'G':
                return OfficeLevel.State;
            default:
                throw new UnsupportedOperationException("Fix This"); // ToDo
        }
    }


}
