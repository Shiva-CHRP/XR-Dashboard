package api.pojo.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateClientRequest {

    @JsonProperty("companyName")
    private String companyName;

    @JsonProperty("contactEmail")
    private String contactEmail;

    @JsonProperty("phone")
    private String phone;

    public CreateClientRequest() {}

    public CreateClientRequest(String companyName, String contactEmail, String phone) {
        this.companyName = companyName;
        this.contactEmail = contactEmail;
        this.phone = phone;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
