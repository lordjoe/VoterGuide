package com.lordjoe.voter.reader;

import com.lordjoe.voter.Politicians;
import com.lordjoe.voter.Races;
import com.lordjoe.voter.votesmart.MyVoteSmart;

import java.io.File;

/**
 * com.lordjoe.voter.reader.public class VoteSmartReader {
   read data from WGet Votesmart
 * User: Steve
 * Date: 6/24/2016
 */
public class VoteSmartReader {

    private static File baseFile;

    public static File getBaseFile() {
        return baseFile;
    }

    public static void setBaseFile(File baseFile) {
        VoteSmartReader.baseFile = baseFile;
    }

    public static void readVoteSmart() {
        File base = getBaseFile();
        File candidates = getCandidatesDirectory(base);
     //    CandidateReader.readCandidates(candidates);
       CandidateReader.readCandidatesFile(base);

        File elections = getElectionsDirectory(base);
        ElectionReader.readElections(elections,Integer.parseInt(MyVoteSmart.CURRENT_YEAR));

     }

    private static File getElectionsDirectory(File base) {
         File election = getSubDirectory(base, "election");
        return getSubDirectory(election, MyVoteSmart.CURRENT_YEAR);
     }

    public static File getCandidatesDirectory(File base) {
        return getSubDirectory(base,"candidate");

    }
    public static File getVoteSmareOrgDirectory(File base) {
        File ret = new File(base,"VoteSmart.org");
        if(ret == null || !ret.isDirectory())
             throw new IllegalStateException("bad directory ");
        return ret;
    }

    private static File getSubDirectory(File base,String name) {
        File ret = new File(base,name);
        if(ret == null || !ret.isDirectory())
            throw new IllegalStateException("bad directory ");
        return ret;
    }


    public static void main(String[] args) {

         File base = new File(args[0]);
        File voteSmart =  getVoteSmareOrgDirectory(base);
        setBaseFile(voteSmart);
        readVoteSmart( );
      //  Politicians.writePoliticians(new File("candidates.txt"));
        Races.listRaces(System.out);
    }



}
