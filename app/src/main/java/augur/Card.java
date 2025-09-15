package augur;

public class Card {
    
    private String name;
    public String name_short;
    public String value;
    public int value_int;
    public String suit;
    public String type;
    public String meaning_up;
    public String meaning_rev;
    public String desc;

    public Card() {}

    public Card(String name, String meaning_up, String meaning_rev) {
        this.name = name;
        this.meaning_up = meaning_up;
        this.meaning_rev = meaning_rev;
    }

    // accessor methods
    public String getName() {
        return name;
    }

    public String getNameShort() {
        return name_short;
    }

    public String getValue() {
        return value;
    }

    public int getValueInt() {
        return value_int;
    }

    public String getSuit() {
        return suit;
    }

    public String getType() {
        return type;
    }

    public String getMeaningUp() {
        return meaning_up;
    }

    public String getMeaningRev() {
        return meaning_rev;
    }

    public String getDesc() {
        return desc;
    }

    // Returns a String with the meaning of the card in an HTML <td> to easily enter into the display table.
    // This function will use the correct meaning depending on if the card is upright or reversed.
    public String getCardMeaningAsHTML(boolean reversed) {
        String meaning = "";

        if (reversed) {
            meaning = " <td><strong>" + this.name + "</strong><br><br>This card reversed represents: " + this.meaning_rev
                    + "</td>";
        } else {
            meaning = " <td><strong>" + this.name + "</strong><br><br>This card represents: " + this.meaning_up + "</td>";
        }

        return meaning;
    }

}
