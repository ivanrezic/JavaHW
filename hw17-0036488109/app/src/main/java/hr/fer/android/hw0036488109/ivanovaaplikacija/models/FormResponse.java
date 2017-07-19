package hr.fer.android.hw0036488109.ivanovaaplikacija.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.io.StringReader;

/**
 * Model that represents JSON data fetched from provided path.
 *
 * Created by Ivan on 29.6.2017..
 */
public class FormResponse implements Serializable {

    @SerializedName("avatar_location")
    private String avatarUrl;
    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;
    @SerializedName("phone_no")
    private String phoneNumber;
    @SerializedName("email_sknf")
    private String email;
    @SerializedName("spouse")
    private String spouse;
    @SerializedName("age")
    private int age;

    /**
     * Gets avatar url.
     *
     * @return the avatar url
     */
    public String getAvatarUrl() {
        return avatarUrl;
    }

    /**
     * Gets first name.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Gets last name.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Gets phone number.
     *
     * @return the phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Gets spouse.
     *
     * @return the spouse
     */
    public String getSpouse() {
        return spouse;
    }

    /**
     * Gets age.
     *
     * @return the age
     */
    public String getAge() {
        return String.valueOf(age);
    }
}
