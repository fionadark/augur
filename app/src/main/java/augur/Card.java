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

    // Default constructor
    public Card() {}

    // Mini constructor for testing (only initializes name, meaning_up, and meaning_rev)
    public Card(String name, String meaning_up, String meaning_rev) {
        this.name = name;
        this.meaning_up = meaning_up;
        this.meaning_rev = meaning_rev;
    }

    // Full constructor for testing (initializes all fields)
    public Card(String name, String name_short, String value, int value_int, String suit, String type,
            String meaning_up, String meaning_rev, String desc) {
        this.name = name;
        this.name_short = name_short;
        this.value = value;
        this.value_int = value_int;
        this.suit = suit;
        this.type = type;
        this.meaning_up = meaning_up;
        this.meaning_rev = meaning_rev;
        this.desc = desc;
    }

    // Accessor methods
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

    /**
     * Returns an HTML <td> element containing the card's name and meaning.
     * The meaning displayed depends on whether the card is upright or reversed.
     *
     * @param reversed true if the card is reversed and the reversed meaning should be used; false for upright meaning
     * @return a String representing an HTML <td> element with the card's name and appropriate meaning
     */
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

    /**
     * Returns an HTML <td> element containing the name of the card's position in the tarot spread.
     * The position name varies based on the number of cards in the spread and the card's index.
     *
     * @param numCards the total number of cards in the spread (must be 1, 3, or 10)
     * @param pos the zero-based index of the card in the spread (must be between 0 and 9)
     * @return a String representing an HTML <td> element with the card's position name
     * @throws IllegalArgumentException if numCards or pos are outside valid ranges
     */
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

        // Determine position name based on numCards and pos
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
