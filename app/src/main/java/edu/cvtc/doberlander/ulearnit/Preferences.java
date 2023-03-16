package edu.cvtc.doberlander.ulearnit;

public class Preferences {
    private String firstLanguage;
    private String secondLanguage;
    private String category;
    private String subCategory;
    private String archived;
    private String favorite;
    private String onQuickList;

    public Preferences() {
        this.firstLanguage = "";
        this.secondLanguage = "";
        this.category = "";
        this.subCategory = "";
        this.archived = "";
        this.favorite = "";
        this.onQuickList = "";
    }

    public Preferences(String firstLanguage, String secondLanguage, String category, String archived, String favorite, String onQuickList) {
        this.firstLanguage = firstLanguage;
        this.secondLanguage = secondLanguage;
        this.category = category;
        this.archived = archived;
        this.favorite = favorite;
        this.onQuickList = onQuickList;
    }

    public String getFirstLanguage() {
        return firstLanguage;
    }

    public void setFirstLanguage(String firstLanguage) {
        this.firstLanguage = firstLanguage;
    }

    public String getSecondLanguage() {
        return secondLanguage;
    }

    public void setSecondLanguage(String secondLanguage) {
        this.secondLanguage = secondLanguage;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getArchived() {
        return archived;
    }

    public void setArchived(String archived) {
        this.archived = archived;
    }

    public String getFavorite() {
        return favorite;
    }

    public void setFavorite(String favorite) {
        this.favorite = favorite;
    }

    public String getOnQuickList() {
        return onQuickList;
    }

    public void setOnQuickList(String onQuickList) {
        this.onQuickList = onQuickList;
    }
}
