package com.lordjoe.voter.votesmart;

import java.util.List;

/**
 * com.lordjoe.voter.votesmart.VoteSmartIssueCategory
 * User: Steve
 * Date: 7/1/2016
 */
public enum VoteSmartIssueCategory {
    Abortion("Abortion", 2),
    Abortion_and_Reproductive("Abortion and Reproductive", 75),
    Agriculture_and_Food("Agriculture and Food", 4),
    Animals_and_Wildlife("Animals and Wildlife", 5),
    Arts_Entertainment_and_History("Arts, Entertainment, and History", 7),
    Business_and_Consumers("Business and Consumers", 11),
    Campaign_Finance_and_Elections("Campaign Finance and Elections", 12),
    Civil_Liberties_and_Civil_Rights("Civil Liberties and Civil Rights", 13),
    Crime("Crime", 20),
    Death_Penalty("Death Penalty", 71),
    Defense("Defense", 22),
    Drugs("Drugs", 25),
    Education("Education", 27),
    Employment_and_Affirmative_Action("Employment and Affirmative Action", 3),
    Energy("Energy", 29),
    Environment("Environment", 30),
    Executive_Branch("Executive Branch", 69),
    Federal_State_and_Local_Relations("Federal, State and Local Relations", 72),
    Foreign_Affairs("Foreign Affairs", 32),
    Gambling_and_Gaming("Gambling and Gaming", 73),
    Government_Operations("Government Operations", 36),
    Guns("Guns", 37),
    Health_and_Health_Care("Health and Health Care", 38),
    Housing_and_Property("Housing and Property", 39),
    Immigration("Immigration", 40),
    Indigenous_Peoples("Indigenous Peoples", 74),
    Judicial_Branch("Judicial Branch", 44),
    Labor_Unions("Labor Unions", 43),
    Legislative_Branch("Legislative Branch", 16),
    Marriage_Family_and_Children("Marriage, Family, and Children", 31),
    Military_Personnel("Military Personnel", 47),
    National_Security("National Security", 61),
    Science("Science", 52),
    Senior_Citizens("Senior Citizens", 53),
    Sexual_Orientation_and_Gender_Identity("Sexual Orientation and Gender Identity", 76),
    Stem_Cell_Research("Stem Cell Research", 77),
    Technology_and_Communication("Technology and Communication", 41),
    Trade("Trade", 63),
    Transportation("Transportation", 64),
    Unemployed_and_LowIncome("Unemployed and Low-Income", 67),
    Veterans("Veterans", 66),
    Women("Women", 68);

    public final String name;
    public final Integer id;

    VoteSmartIssueCategory(String name, Integer id) {
        this.name = name;
        this.id = id;
    }

    public String getIdString()
    {
        return id.toString();
    }

    public static VoteSmartIssueCategory getById(Integer id) {
        for (VoteSmartIssueCategory test : values()) {
            if (id == test.id)
                return test;
        }
        return null;
    }

    public static VoteSmartIssueCategory getByName(String name) {
        for (VoteSmartIssueCategory test : values()) {
            if (name.equalsIgnoreCase(test.name))
                return test;
        }
        return null;
    }


}
