package com.lordjoe.voter.reader;

import com.lordjoe.voter.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * com.lordjoe.voter.reader.CandidateReader
 * User: Steve
 * Date: 6/24/2016
 */
public class CandidateReader {

    public static final String OVERVIEW = "accordion individual-overview no-padding";
    public static final String CAMPHOTO = "span-3 canphoto";
    public static final String SPAN7 = "span-7 last";

    public static void readCandidates(File directory) {
        File[] files = directory.listFiles();
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            Politician pol = processCandidateDirectory(file);
            if (pol == null)
                System.out.println("Bad file " + file.getName());

        }
        Politicians.writePoliticians(new File("candidates.txt"));
    }

    public static Politician readCandidate(Integer id) {
        File candidates = new File(VoteSmartReader.getBaseFile(), "candidate");
        File candidateFile = new File(candidates, id.toString());
        Politician pol = processCandidateDirectory(candidateFile);
        return pol;
    }

    public static Politician processCandidateDirectory(File file) {
        Integer id = null;
        try {
            id = new Integer(file.getName());
        } catch (NumberFormatException e) {
            return null; // not what we can handle

        }
        File[] candidateFiles = file.listFiles();
        if (candidateFiles == null) {
            System.out.println("Cannot find " + id);
            return null;
            // throw new UnsupportedOperationException("Fix This"); // ToDo
        }
        if (candidateFiles.length == 0)
            return null; // not what we can handle

        File cf = findCandidateFile(candidateFiles);
        return processCandidateFile(id, cf);

    }

    private static File findCandidateFile(File[] candidateFiles) {
        File ret = null;
        for (int i = 0; i < candidateFiles.length; i++) {
            File candidateFile = candidateFiles[i];
            if (candidateFile.isDirectory())
                continue;
            if (candidateFile.getName().equals("index.html"))
                continue;
            if (candidateFile.getName().contains("@"))
                continue;
            if (ret == null)
                ret = candidateFile;
            else {
                long l1 = ret.length();  // maybe they are the same file
                long l2 = candidateFile.length();
                if (l2 != l1) {
                    //  throw new IllegalStateException("abbiguous file");
                    System.out.println("Anbiguous File " + candidateFile.getName());
                }
            }
        }
        return ret;
    }

    private static Politician processCandidateFile(Integer id, File candidateFile) {

        try {
            List<String> lines = JSoupUtilities.readInFile(candidateFile);
            String text = JSoupUtilities.mergeLines(lines);
            Politician pol = getPoliticianFromLines(id, lines);
            if (pol == null) {
                pol = getPoliticianFromLines(id, lines);
                return null; // give up
            }
            Document doc = Jsoup.parse(candidateFile, "UTF-8");
            Element overview = JSoupUtilities.getOneSubItemOfClass(doc, OVERVIEW);
            String photo = getPhotoURL(overview);
            pol.setImageURL(photo);

            if (false) {
//                text = JSoupUtilities.removeHTMLComments(text);
//                JSoupUtilities.writeFile(candidateFile.getName() + ".html", text);
//
//                InputStream inputStream = JSoupUtilities.fromString(text);
//                Document doc = Jsoup.parse(inputStream, "UTF-8", "file://" + candidateFile.getCanonicalPath());
//                Element overview = JSoupUtilities.getOneSubItemOfClass(doc, OVERVIEW);
//                processOverview(id, overview);
            }

            return pol;

        } catch (IOException e) {
            throw new RuntimeException(e);

        }

    }

    public static final String NAME_LINE_START = "<h3 itemprop=\"description\"><span itemprop=\"name\">";

    private static String getNameLine(List<String> lines) {
        for (String line : lines) {
            int index = line.indexOf(NAME_LINE_START);
            if (index > -1) {
                String ret = line.substring(index + NAME_LINE_START.length());
                int endIndex = ret.indexOf("&#39;");
                if (endIndex > -1)
                    return ret.substring(0, endIndex);
                endIndex = ret.indexOf("\'s Political Summary");
                if (endIndex > -1)
                    return ret.substring(0, endIndex);

            }
        }
        return null; // not found
    }

    private static Politician getPoliticianFromLines(Integer id, List<String> lines) {
        Politician ret = null;
        String nameLine = getNameLine(lines);
        if (nameLine == null)
            return null;
        String[] split = nameLine.split(" ");
        if (split.length < 2)
            return null;
        String firstName = split[0];
        String lastName = buildLastName(split);
        ret = Politicians.get(id, firstName, lastName);

        return ret;
    }

    private static String buildLastName(String[] split) {
        if (split.length == 2)
            return split[1];
        StringBuilder sb = new StringBuilder();
        sb.append(split[1]);
        for (int i = 2; i < split.length; i++) {
            String s = split[i];
            sb.append(" " + s);
        }
        return sb.toString();

    }

    private static Politician processOverview(Integer id, Element overview) {
        String photo = getPhotoURL(overview);
        PersonalInformation pi = getPersonalInfo(overview);
        Politician ret = buildBasicPolitician(id, overview);
        ret.setImageURL(photo);
        ret.getInfo().setFrom(pi);
        return ret;
    }


    private static Politician buildBasicPolitician(Integer id, Element overview) {
        throw new UnsupportedOperationException("Fix This"); // ToDo

    }


    private static String getPhotoURL(Element overview) {
        String url = null;
        Element camphoto = JSoupUtilities.getOneSubItemOfClass(overview, CAMPHOTO);
        Elements select = camphoto.select("a");
        url = select.first().attr("href");
        return url;
    }

    private static PersonalInformation getPersonalInfo(Element overview) {

        Elements table = JSoupUtilities.getOneSubItemOfClass(overview, SPAN7).select("table");
        PersonalInformation pi = new PersonalInformation();
        Map<String, String> props = JSoupUtilities.parsePropertyTable(table.first());
        setPersonalInformationProperties(pi, props);

        return pi;
    }

    private static void setPersonalInformationProperties(PersonalInformation pi, Map<String, String> props) {

    }


    public static void readCandidatesFile(File base) {
        List<String> strings = null;
        InputStream resourceAsStream = CandidateReader.class.getResourceAsStream("/candidates.txt");
        if(resourceAsStream != null )  {
            strings = JSoupUtilities.readInFile(resourceAsStream);
            for (String line : strings) {
                processCandidateResourceString(line);
            }
        }
        else {
            File data = new File(base, "candidates.txt");
            strings = JSoupUtilities.readInFile(data);
            for (String line : strings) {
                processCandidateString(line);
            }
        }
      }

    private static void processCandidateResourceString(String line) {
        String[] items = line.split("\t");
        int index = 0;
        boolean incumbant = false;
        if(items.length > 3 && items[3].equals("Incumbent"))
            incumbant = true;

        int jindex = 0;
        String raceStr = items[index++];
        String[] raceItems = raceStr.split(" ");
        State s = State.fromString(raceItems[0] );
        if(s == null)
            throw new UnsupportedOperationException("Fix This"); // ToDo
        OfficeType type = OfficeType.valueOf(raceItems[raceItems.length - 1]);
        Integer district = null;
        try {
            if(type == OfficeType.Congressman && raceItems.length > 2)
                    district = new Integer(raceItems[1]) ;
        } catch (NumberFormatException e) {
            district = 0;

        }

        String idStr = items[index++];
        jindex = 0;
        String[] idItems = idStr.split(" ");
        String firstName = idItems[jindex++];
        StringBuilder sb = new StringBuilder(idItems[jindex++]);
        while(jindex < idItems.length - 3)   {
            sb.append(" " + idItems[jindex++]);
        }


        String lastName = sb.toString();

        Integer id = null;
       id = new Integer(idItems[idItems.length - 1]);

        Party party = Party.valueOf(items[index++]);

        ElectionStage stage = ElectionStage.valueOf(items[index++]);


        Politician politician = Politicians.get(id, firstName, lastName);
      }

    private static void processCandidateString(String line) {
        String[] items = line.split("\t");
        int index = 0;
        String idStr = items[index++];
        if (idStr.charAt(0) > '9')
            idStr = idStr.substring(1);
        Integer id = new Integer(idStr);
        String firstName = items[index++];
        String lastName = items[index++];
        String photo = items[index++];
        Politician politician = Politicians.get(id, firstName, lastName);
        politician.setImageURL(photo);
    }
}
