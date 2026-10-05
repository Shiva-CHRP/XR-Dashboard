package superadmin.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BrandingDetails {

	@JsonProperty("portalName")
	private String portalName;

	@JsonProperty("logoPath")
	private String logoPath;

	@JsonProperty("faviconPath")
	private String faviconPath;

	@JsonProperty("primaryColor")
	private String primaryColor;

	@JsonProperty("navTheme")
	private String navTheme;

	@JsonProperty("appearance")
	private String appearance;

	@JsonProperty("templateVariant")
	private String templateVariant;

	public BrandingDetails() {
	}

	public BrandingDetails(String portalName, String logoPath, String faviconPath, String primaryColor,
			String navTheme, String appearance, String templateVariant) {
		this.portalName = portalName;
		this.logoPath = logoPath;
		this.faviconPath = faviconPath;
		this.primaryColor = primaryColor;
		this.navTheme = navTheme;
		this.appearance = appearance;
		this.templateVariant = templateVariant;
	}

	public String getPortalName() {
		return portalName;
	}

	public void setPortalName(String portalName) {
		this.portalName = portalName;
	}

	public String getLogoPath() {
		return logoPath;
	}

	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}

	public String getFaviconPath() {
		return faviconPath;
	}

	public void setFaviconPath(String faviconPath) {
		this.faviconPath = faviconPath;
	}

	public String getPrimaryColor() {
		return primaryColor;
	}

	public void setPrimaryColor(String primaryColor) {
		this.primaryColor = primaryColor;
	}

	public String getNavTheme() {
		return navTheme;
	}

	public void setNavTheme(String navTheme) {
		this.navTheme = navTheme;
	}

	public String getAppearance() {
		return appearance;
	}

	public void setAppearance(String appearance) {
		this.appearance = appearance;
	}

	public String getTemplateVariant() {
		return templateVariant;
	}

	public void setTemplateVariant(String templateVariant) {
		this.templateVariant = templateVariant;
	}

	@Override
	public String toString() {
		return "BrandingDetails{" +
				"portalName='" + portalName + '\'' +
				", logoPath='" + logoPath + '\'' +
				", faviconPath='" + faviconPath + '\'' +
				", primaryColor='" + primaryColor + '\'' +
				", navTheme='" + navTheme + '\'' +
				", appearance='" + appearance + '\'' +
				", templateVariant='" + templateVariant + '\'' +
				'}';
	}
}
