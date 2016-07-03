package  com.lordjoe.voter;

/**
 * main.java.com.lordjoe.voter.Office
 * User: Steve
 * Date: 6/23/2016
 */
public enum OfficeType {
    President(OfficeLevel.Federal),
    Senator(OfficeLevel.Federal),
    Congressman(OfficeLevel.Federal),
    Governor(OfficeLevel.State),
    LeutenantGovernor(OfficeLevel.State),
    AttorneyGeneral(OfficeLevel.State);

    public final OfficeLevel level;
    private OfficeType(OfficeLevel l) {
        level = l;
    }
}
