package com.lordjoe.voter.votesmart;

import com.lordjoe.utilities.CollectionUtilities;
import com.lordjoe.utilities.DualList;
import com.lordjoe.utilities.DualMap;
import com.lordjoe.voter.*;
import com.lordjoe.voter.State;
import org.votesmart.api.VoteSmartErrorException;
import org.votesmart.api.VoteSmartException;
import org.votesmart.classes.*;
import org.votesmart.data.*;

import java.io.*;
import java.util.*;

/**
 * com.lordjoe.voter.reader.VoteSmart
 * User: Steve
 * Date: 6/25/2016
 */
public class MyVoteSmart {
    public static final String CURRENT_YEAR = "2016";
    public static final String FIRST_INTERESTING_YEAR = "2009";
    public static final String DISTRICTS_FILE = "VoteSmartDistricts.txt";
    private static final String CANDIDATES_FILE = "Candidates.txt";
    private static Map<State, String> gStateToCongressionalElectionId;
    private static Map<State, String> gStateToStateElectionId;
    private static Map<String, OfficeLevel> gStringToOfficeLevel = new HashMap<>();

    private static DualMap<State, String> gStateToString = new DualMap<>(State.class, String.class);
    private static DualMap<OfficeType, String> gOfficeTypeToString = new DualMap<>(OfficeType.class, String.class);
    private static DualMap<Candidate, String> gCandidateToStringID = new DualMap<>(Candidate.class, String.class);

    private static DualList<District, String> gDistrictToStringID = new DualList<>(District.class, String.class);


    public static List<District> getDistricts() {
        initIfNeeded();
        Set<District> districts = gDistrictToStringID.reverseValues();
        List<District> ret =  CollectionUtilities.sortedCollection(districts);
          return ret;
    }

    public static void initIfNeeded() {
        if (gStateToString.isEmpty()) {
            initIdToState();
            initOffices();
            initDistricts();
            initElections();
            initOfficials();
            VoteSmartVotes.initIfNeeded();
             // commented out to speed up
         //   VoteSmartRating.initIfNeeded();
         //   VoteSmartQuestionaire.initIfNeeded();
           // VoteSmartBio.initIfNeeded();
            writeCandidates(CANDIDATES_FILE);

        }

    }

    public static String stateToId(State st) {
        initIfNeeded();
        return gStateToString.forwardLookup(st);
    }

    public static State idToState(String st) {
        initIfNeeded();
        return gStateToString.reverseLookup(st);
    }

    private static void writeDistricts(String fileName) {
        try {
            PrintWriter out = new PrintWriter(new FileWriter(fileName));
            Set<District> districts = gDistrictToStringID.reverseValues();
            List<District> sorted = new ArrayList<>(districts);
            Collections.sort(sorted);
            for (District district : sorted) {
                out.print(district.type);
                out.print("\t");
                out.print(district.state);
                out.print("\t");
                out.print(district.number);
                out.print("\t");
                Set<String> strings = gDistrictToStringID.forwardLookup(district);
                List<String> sortedId = new ArrayList<>(strings);
                Collections.sort(sortedId);
                if (sorted.size() == 1) {
                    out.print(sortedId.get(0));
                } else {
                    boolean first = true;
                    for (String id : sortedId) {
                        if (!first)
                            out.print(",");
                        out.print(id);
                        first = false;
                    }

                }
                out.println();
            }
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);

        }
    }


    private static void readDistricts(File test) {
        try {
            LineNumberReader rdr = new LineNumberReader(new FileReader(test));
            String line = rdr.readLine();
            while (line != null) {
                String[] items = line.split("\t");
                int index = 0;
                OfficeType type = OfficeType.valueOf(items[index++]);
                State state = State.valueOfName(items[index++]);
                String name = items[index++];
                District ds = Districts.getDistrict(type, state, name);

                String votesmartId = items[index++];
                String[] split = votesmartId.split(",");
                for (int i = 0; i < split.length; i++) {
                    String s = split[i];
                    gDistrictToStringID.register(ds, votesmartId);
                }
                line = rdr.readLine();
            }

            rdr.close();
        } catch (IOException e) {
            throw new RuntimeException(e);

        }
    }


    private static void initOfficials() {
        initIfNeeded();
        OfficialsClass oc = null;
        try {
            oc = new OfficialsClass();
        } catch (VoteSmartException e) {
            throw new RuntimeException(e);

        }
        // for types we care about
        for (OfficeType officeType : gOfficeTypeToString.reverseValues()) {
            String officeStr = gOfficeTypeToString.forwardLookup(officeType);
            for (State state : State.values()) {
                if(state == State.ARIZONA)
                    breakHere();
                String statStr = gStateToString.forwardLookup(state);
                CandidateList byOfficeState = null;
                try {
                    byOfficeState = oc.getByOfficeState(officeStr, statStr);
                } catch (VoteSmartException e) {
                    continue;
                } catch (VoteSmartErrorException e) {
                    switch(officeType ) {
                        case Senator:
                            if(state.isSenators())
                                throw new IllegalStateException("problem"); // ToDo change
                            break;
                        case Governor:
                            if(state.isSenators())
                                throw new IllegalStateException("problem"); // ToDo change
                            break;
                        default:
                            throw new IllegalStateException("problem"); // ToDo change
                    }
                    continue;
                }
                for (CandidateList.Candidate candidate : byOfficeState.candidate) {
                    String candidateId = candidate.candidateId;
                    String officeName = candidate.officeName;
                    String officeDistrictName = candidate.officeDistrictName;
                    String officeDistrictId = candidate.officeDistrictId;
                    Integer districtNumber = null;
                    if(!"".equals(officeDistrictId))  {
                        try {
                            districtNumber = Integer.parseInt(officeDistrictName);
                        } catch (NumberFormatException e) {
                            districtNumber = null;
                     }
                    }
                    OfficeType type = typeFromName(officeName);
                    String firstName = candidate.firstName;
                    String lastName = candidate.lastName;
                    District district1 = Districts.getDistrict(type,state,districtNumber);
                    if(district1 == null)
                        continue;
                    Politician incumbent = Politicians.getByName(firstName,lastName);
                    if (incumbent != null) {
                        district1.setIncumbent(incumbent);
                    }
                    else {
                        Integer id = Integer.parseInt(candidateId);
                        district1.setIncumbent(Politicians.get(id,firstName,lastName));
                    }

                }
            }
        }

    }

    private static OfficeType typeFromName(String officeName) {
        try {
            OfficeType ret = OfficeType.valueOf(officeName);
            return ret;
        } catch (IllegalArgumentException e) {
         }
          if("U.S. Senate".equalsIgnoreCase(officeName))
            return OfficeType.Senator;
          if("U.S. House".equalsIgnoreCase(officeName))
            return OfficeType.Congressman;
        if("Congressman".equalsIgnoreCase(officeName))
            return OfficeType.Congressman;
        return null;
    }


    public static Politician getPolitician(Integer id) {
        initIfNeeded();
        try {
            CandidatesClass candidatesClass = new CandidatesClass();
            // candidatesClass.
            throw new UnsupportedOperationException("Fix This"); // ToDo
        } catch (VoteSmartException e) {
            throw new RuntimeException(e);

        }
    }

    private static void initializeDistricts() {
        State[] values = State.values();
        for (int i = 0; i < values.length; i++) {
            State value = values[i];
            initializeDistricts(value);
        }
    }

    private static void initializeDistricts(State state) {

    }

    private static synchronized void initOffices() {
        //  gStringToOfficeLevel.put("F",OfficeLevel.Federal);
        gStringToOfficeLevel.put("C", OfficeLevel.Federal);
        gStringToOfficeLevel.put("L", OfficeLevel.State);
        gStringToOfficeLevel.put("S", OfficeLevel.State);
        gStringToOfficeLevel.put("G", OfficeLevel.State);

        gOfficeTypeToString.register(OfficeType.Congressman, "5");
        gOfficeTypeToString.register(OfficeType.Senator, "6");
        gOfficeTypeToString.register(OfficeType.Governor, "3");
   //    gOfficeTypeToString.register(OfficeType.LeutenantGovernor, "4");


        try {
            OfficeClass oc = new OfficeClass();
            Levels levels = oc.getLevels();
            for (Levels.Level level : levels.level) {
                String officeLevelId = level.officeLevelId;
                if (gStringToOfficeLevel.get(officeLevelId) == null)
                    break;
                Offices officesByLevel = oc.getOfficesByLevel(officeLevelId);

                for (Offices.Office office : officesByLevel.office) {
                    String officeId = office.officeId;
                    String officeTypeId = office.officeTypeId;
                    String shortTitle = office.shortTitle;
                }
            }
        } catch (VoteSmartException e) {
            throw new RuntimeException(e);

        } catch (VoteSmartErrorException e) {
            throw new RuntimeException(e);

        }

    }

    private static synchronized void initIdToState() {
        try {


            // Determine California State Id
            StateClass stateClass = new StateClass();
            StateList allStates = stateClass.getStateIDs();
            StateList.List.State state = null;
            for (StateList.List.State aState : allStates.list.state) {
                State st = State.valueOfName(aState.name);
                if (st != null) {
                    String id = aState.stateId;
                    gStateToString.register(st, id);
                }
            }
        } catch (VoteSmartException e) {
            throw new RuntimeException(e);

        } catch (VoteSmartErrorException e) {
            throw new RuntimeException(e);

        }

    }


    private static synchronized void initElections() {
        try {
            gStateToCongressionalElectionId = new HashMap<>();
            gStateToStateElectionId = new HashMap<>();
            // Determine California State Id
            ElectionClass ec = new ElectionClass();
            for (State state : gStateToString.reverseValues()) {
                if (state.equals(State.ARIZONA))
                    breakHere();
                String stateKey = gStateToString.forwardLookup(state);
                Elections electionByYearState = null;
                try {
                    electionByYearState = ec.getElectionByYearState(CURRENT_YEAR, stateKey);
                } catch (VoteSmartException e) {
                    continue;

                } catch (VoteSmartErrorException e) {
                    continue;

                }
                for (Elections.ElectionStage electionStage : electionByYearState.election) {

                    String electionId = electionStage.electionId;
                    ElectionStage stage = ElectionStage.General;     // general
                    StageCandidates candidates = null;
                    // try the general
                    try {
                        candidates = ec.getStageCandidates(electionId, stage.votesmartID);
                    } catch (VoteSmartException e) {
                    } catch (VoteSmartErrorException e) {
                    }
                    // try the primary
                    if (candidates == null) {
                        stage = ElectionStage.Primary;     // primary
                        try {
                            candidates = ec.getStageCandidates(electionId, stage.votesmartID);
                        } catch (VoteSmartException e) {
                        } catch (VoteSmartErrorException e) {
                        }
                    }
                    if (candidates == null)
                        continue;


                    ArrayList<StageCandidates.Candidate> candidate1 = candidates.candidate;
                    OfficeType type = null;
                    for (StageCandidates.Candidate thisCandidate : candidate1) {
                        String officeId = thisCandidate.officeId;
                        type = gOfficeTypeToString.reverseLookup(officeId);
                        if (type == null)
                            continue;    // we do not care about this
                        if (type == OfficeType.Senator)  // look at this case
                            breakHere();
                        if (type == OfficeType.Congressman)  // look at this case
                            breakHere();

                        String districtId = thisCandidate.district;
                        District district = Districts.getDistrict(type, state, districtId);


                        String candidateId = thisCandidate.candidateId;
                        String firstName = thisCandidate.firstName;
                        String lastName = thisCandidate.lastName;
                        String partyName = thisCandidate.party;
                        // now build my idea of a candidate
                        Politician myCandidate = Politicians.get(Integer.parseInt(candidateId), firstName, lastName);
                        Party party = Party.fromName(partyName);

                        Race race = Races.getRace(district, Integer.parseInt(CURRENT_YEAR),stage);
                        Candidate candidate =  Candidates.getCandidate(myCandidate, party, race);
                        gCandidateToStringID.register(candidate, candidateId);
                    }

                    if (type != null) {

                        OfficeLevel officeLevel = type.level;
                        if (officeLevel == null)
                            continue;
                        switch (officeLevel) {
                            case Federal:
                                gStateToCongressionalElectionId.put(state, electionId);
                                break;
                            case State:
                                gStateToStateElectionId.put(state, electionId);
                                break;
                        }
                    }

                }
            }
          } catch (VoteSmartException e) {
            throw new RuntimeException(e);
        }

    }

    public static void breakHere() {
    }

    private static void writeCandidates(String fileName) {
        List<Candidate> candidates = new ArrayList(gCandidateToStringID.reverseValues());
        Collections.sort(candidates, Candidate.BY_DISTRICT);
        try {
            PrintWriter out = new PrintWriter(new FileWriter(fileName));
            for (Candidate candidate : candidates) {
                writeCandidate(candidate, out);
            }
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);

        }


    }

    private static void writeCandidate(Candidate candidate, PrintWriter out) {
        Race race = candidate.race;
        writeRace(race, out);
        writePolitician(candidate.pol, out);
        out.print(candidate.party);
        out.print("\t");
        out.print(race.getStage());

        if(candidate.isIncumbent())  {
            out.print("\tIncumbent");
        }

        out.println();
    }

    private static void writePolitician(Politician pol, PrintWriter out) {
        out.print(pol);
        out.print("\t");

    }

    private static void writeRace(Race race, PrintWriter out) {
        out.print(race.district);
        out.print("\t");
    }

    private static OfficeType officeIdToType(String officeId) {
        if (officeId.equals("C"))
            return OfficeType.Congressman;
        if (officeId.equals("S"))
            return OfficeType.Senator;
        if (officeId.equals("G"))
            return OfficeType.Governor;
        return null;

    }

    private static Integer numberFromString(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return null;

        }
    }


    private static synchronized void initDistricts() {
        File test = new File(DISTRICTS_FILE);
        if (test.exists()) {
            readDistricts(test);
            return;
        }
        try {
            gStateToCongressionalElectionId = new HashMap<>();
            gStateToStateElectionId = new HashMap<>();
            // Determine California State Id
            DistrictClass districts = new DistrictClass();
            for (State state : gStateToString.reverseValues()) {
                String stateKey = gStateToString.forwardLookup(state);
                for (OfficeType officeType : gOfficeTypeToString.reverseValues()) {
                    String officeStr = gOfficeTypeToString.forwardLookup(officeType);
                    try {
                        DistrictList byOfficeState = districts.getByOfficeState(officeStr, stateKey);
                        if (byOfficeState == null)
                            continue;

                        ArrayList<DistrictList.District> electionDistricts = byOfficeState.district;
                        if (electionDistricts == null)
                            continue;


                        for (DistrictList.District district : electionDistricts) {
                            String districtId = district.districtId;
                            String name = district.name;
                            District ds = Districts.getDistrict(officeType, state, name);
                            gDistrictToStringID.register(ds, districtId);
                        }
                    } catch (VoteSmartException e) {
                        continue; //

                    } catch (VoteSmartErrorException e) {
                        continue; //
                    }

                }
                System.out.println("Read Districts " + state);
            }
            writeDistricts(DISTRICTS_FILE);
        } catch (VoteSmartException e) {
            throw new RuntimeException(e);

        }
    }


}
