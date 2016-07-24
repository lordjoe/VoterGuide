package com.lordjoe.voter;

import com.lordjoe.utilities.DualList;
import com.lordjoe.voter.votesmart.VoteSmartIssueCategory;

/**
 * com.lordjoe.voter.Issues
 * User: Steve
 * Date: 7/4/2016
 */
public class Issues {

    private static DualList<IssueCategory,VoteSmartIssueCategory>  toVotesmartCategories = new DualList<IssueCategory,VoteSmartIssueCategory>(IssueCategory.class,VoteSmartIssueCategory.class);
    private static DualList<IssueCategory, Issue> byIssue = new DualList<IssueCategory, Issue>(IssueCategory.class, Issue.class);

    private void initIfNeeded()
    {
        if(!toVotesmartCategories.isEmpty())
            return;
        initCategories();
        initIssues();
    }

    private void initCategories() {

        toVotesmartCategories.register(IssueCategory.Budget,VoteSmartIssueCategory.Government_Operations);
        toVotesmartCategories.register(IssueCategory.Campaign_Finance,VoteSmartIssueCategory.Campaign_Finance_and_Elections);
        toVotesmartCategories.register(IssueCategory.Economy,VoteSmartIssueCategory.Business_and_Consumers);
        toVotesmartCategories.register(IssueCategory.Education,VoteSmartIssueCategory.Education);
        toVotesmartCategories.register(IssueCategory.Environment,VoteSmartIssueCategory.Environment);
        toVotesmartCategories.register(IssueCategory.Gay_Marriage,VoteSmartIssueCategory.Marriage_Family_and_Children);
        toVotesmartCategories.register(IssueCategory.Healthcare,VoteSmartIssueCategory.Abortion);
        toVotesmartCategories.register(IssueCategory.Healthcare,VoteSmartIssueCategory.Health_and_Health_Care);
        toVotesmartCategories.register(IssueCategory.Gun_Control,VoteSmartIssueCategory.Guns);
        toVotesmartCategories.register(IssueCategory.Healthcare,VoteSmartIssueCategory.Abortion_and_Reproductive);
        toVotesmartCategories.register(IssueCategory.Immigration,VoteSmartIssueCategory.Civil_Liberties_and_Civil_Rights);
        toVotesmartCategories.register(IssueCategory.Immigration,VoteSmartIssueCategory.Immigration);
        toVotesmartCategories.register(IssueCategory.Immigration,VoteSmartIssueCategory.Foreign_Affairs);
        toVotesmartCategories.register(IssueCategory.Iraq,VoteSmartIssueCategory.Foreign_Affairs);
        toVotesmartCategories.register(IssueCategory.Iraq,VoteSmartIssueCategory.Veterans);
        toVotesmartCategories.register(IssueCategory.Social_Issues,VoteSmartIssueCategory.Marriage_Family_and_Children);
        toVotesmartCategories.register(IssueCategory.Social_Issues,VoteSmartIssueCategory.Marriage_Family_and_Children);
        toVotesmartCategories.register(IssueCategory.Supreme_Court,VoteSmartIssueCategory.Judicial_Branch);
        toVotesmartCategories.register(IssueCategory.Taxes,VoteSmartIssueCategory.Business_and_Consumers);
        toVotesmartCategories.register(IssueCategory.Voting_Rights,VoteSmartIssueCategory.Civil_Liberties_and_Civil_Rights);
    }




    public static void buildIssue(String issue, String question) {
        issue = issue.trim();
        question = question.trim();
        throw new UnsupportedOperationException("Fix This"); // ToDo
    }

    private static void initIssues() {
        buildIssue("Campaign Finance", "Do you support the DISCLOSE Act, which would require key funders of political ads to put their names on those ads");
        buildIssue("Campaign Finance", "Do you support the Supreme Court’s Citizens United decision, which allows unlimited independent political expenditures by corporations and unions");
        buildIssue("Courts", "Did you support the Senate holding hearings to consider Obama Supreme Court nominee Merrick Garland"); //Same question; new initial label and therefore new location
        buildIssue("Crime", "Should money be switched from prisons to preventive measures like education and social services");
        buildIssue("Crime", " Do you support efforts to decriminalize and/or legalize marijuana");
        buildIssue("Supreme Court", "Did you support the Senate holding hearings to consider Obama Supreme Court nominee Merrick Garland");
        buildIssue("Economy", "Do you support raising the federal minimum wage");
        buildIssue("Economy", "Do you support the Dodd-Frank act, which established the Consumer Financial Protection Bureau and seeks to increase regulation of Wall Street corporations and other financial institutions");
        buildIssue("Economy", "Do you support federal spending as a means of promoting economic growth");
        buildIssue("Education", "Do you support refinancing of student loans at lower rates, paid for by increasing taxes on high earners");
        buildIssue("Education", "Should federal student financial aid, like Pell Grants, be increased");
        buildIssue("Education", "Should the federal government support universal pre-Kindergarten");
        buildIssue("Environment", "Do you believe that human activity is a major factor contributing to climate change");
        buildIssue("Environment", "Do you support government action to limit the levels of greenhouse gasses in the atmosphere");
        buildIssue("Environment", "Do you support government mandates and/or subsidies for renewable energy");
        buildIssue("Gay Marriage", "Do you support gay marriage");
        buildIssue("Gun Control", "Do you support enacting more restrictive gun control legislation");
        buildIssue("Healthcare", "Do you support repealing the Affordable Care Act, also known as Obamacare");
        buildIssue("Healthcare", "Did you support shutting down the federal government in order to defund Obamacare in 2013");
        buildIssue("Healthcare", "Should Planned Parenthood receive public funds for non-abortion health services"); //Same question; new initial label and therefore new location
        buildIssue("Immigration", "Do you support the D.R.E.A.M. Act, which would allow children brought into the country illegally to achieve legal status if they’ve graduated from high school, have a clean legal record, and attend college or serve in the military");
        buildIssue("Immigration", "Should America’s 11 million undocumented residents have an earned path to citizenship");
        buildIssue("Iran", "Do you support the US-Iran treaty that limits Iran’s nuclear capability in return for lifting economic sanctions");
        buildIssue("Iraq", "Should the US recommit significant additional ground troops to Iraq to combat the success of ISIS");
        buildIssue("Social Issues", "Should abortion be highly restricted");
        buildIssue("Social Issues", " Should employers be able to withhold contraceptive coverage from employees if they disagree with it morally");
        buildIssue("Social Issues", "Should transgender individuals have the right to use public bathrooms of their choice");
        buildIssue("Social Security", " Do you support full or partial Social Security privatization");
        buildIssue("Taxes", "Have you signed the Americans for Tax Reform Pledge to oppose any tax increases to raise revenue");
        buildIssue("Taxes", " Would you increase taxes on corporations and/or high-income individuals to pay for public services");
        buildIssue("Voting rights", " Do you support strict voter ID rules even if that makes it harder for some people to vote");
    }


    public static void registerIssue(IssueCategory category, Issue issue) {
        byIssue.register(category,issue);
    }
}
