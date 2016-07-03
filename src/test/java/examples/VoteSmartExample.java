package examples;

import com.lordjoe.voter.*;
import com.lordjoe.voter.votesmart.MyVoteSmart;
import com.lordjoe.voter.votesmart.VoteSmartVotes;

import java.util.List;

/**
 * examples.VoteSmartExample
 * User: Steve
 * Date: 6/29/2016
 */
public class VoteSmartExample {

    public static void main(String[] args) {

        MyVoteSmart.initIfNeeded();


        Politician pol = Politicians.getByName("John","Boozman");
        District district = Districts.getDistrict(OfficeType.Senator,State.ARKANSAS);
        Race race = Races.getRace(district,2016);

        Candidate candidate = race.getCandidate(pol);

         System.out.println(candidate);

    }


}
