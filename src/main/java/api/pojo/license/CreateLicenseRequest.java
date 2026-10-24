package api.pojo.license;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateLicenseRequest {

    @JsonProperty("clientId")
    private String clientId;

    @JsonProperty("productName")
    private String productName;

    @JsonProperty("maxUsers")
    private Integer maxUsers;

    @JsonProperty("expiryDate")
    private String expiryDate;

    public CreateLicenseRequest() {}

    public CreateLicenseRequest(String clientId, String productName, Integer maxUsers, String expiryDate) {
        this.clientId = clientId;
        this.productName = productName;
        this.maxUsers = maxUsers;
        this.expiryDate = expiryDate;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getMaxUsers() {
        return maxUsers;
    }

    public void setMaxUsers(Integer maxUsers) {
        this.maxUsers = maxUsers;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }
}
