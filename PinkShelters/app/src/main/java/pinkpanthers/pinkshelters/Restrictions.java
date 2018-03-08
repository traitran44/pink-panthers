package pinkpanthers.pinkshelters;

/**
 * Created by hdang on 3/8/18.
 */

public enum Restrictions {
    MEN("men"),
    WOMEN("women"),
    CHILDREN("children"),
    YOUNG_ADULTS("young_adults"),
    FAMILIES_W_NEWBORNS("families_w_newborns"),
    ANYONE("anyone");

    private String sqlRestriction;
    Restrictions(String restriction) {
        this.sqlRestriction = restriction;
    }

    public String toString() {
        return sqlRestriction;
    }
}
