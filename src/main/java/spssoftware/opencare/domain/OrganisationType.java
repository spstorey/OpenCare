package spssoftware.opencare.domain;

public enum OrganisationType {

    GP_SURGERY("GP Surgery"),
    HOSPITAL("GP Surgery"),
    CHIROPRACTOR_SURGERY("Chiropractor Surgery"),
    PHYSIOTHERAPIST_SURGERY("Physiotherapist Surgery");

    String name;

    private OrganisationType(String name) {}

    public String getName() {
        return name;
    }
}