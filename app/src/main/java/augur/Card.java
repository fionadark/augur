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

    // Returns a String with the name of the card's position in the spread
    // 1st position is "Your Past", 2nd position is "Your Present", 3rd position is "Your Future", etc.
    public static String getCardPosName(int numCards, int pos) {
        String[] positionNames = { "Your Past", "Your Present", "Your Future", "Your Current Challenge", "Your Conscious",
                "Your Subconscious", "The Cards Advice", "Your External Influences", "Your Hopes and Fears",
                "The Outcome" };
        String cardPosName = "";

        // Check for invalid arguments
        if (numCards < 1 || numCards > 10 || pos < 0 || pos > 9) {
            System.out.println("numCards: " + numCards);
            System.out.println("pos: " + pos);
            throw new IllegalArgumentException("invalid argument");
        }

        // Set cardPosName depending on the meaning of the card's position in the spread
        if (numCards == 1) {
            cardPosName = " <td>" + positionNames[2] + "</td>";
        } else if (numCards == 3 || numCards == 10) {
            cardPosName = " <td>" + positionNames[pos] + "</td>";
        } else {
            cardPosName = " <td>The Future </td>";
        }

        return cardPosName;
    }

}
