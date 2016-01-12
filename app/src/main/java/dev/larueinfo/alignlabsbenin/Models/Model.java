package dev.larueinfo.alignlabsbenin.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Sergeui Ali Gorbatch on 27/12/2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Model {
    private Long stackId;
    private Long time;
    private String titre;
    private String grdTitre;
    private String auteur;
    private String imagePrincipale;
    private String desc;
    private String ptitre1;
    private String desc_pTitre1;
    private String img_pTitre1;
    private String ptitre2;
    private String desc_pTitre2;
    private String img_pTitre2;
    private String ptitre3;
    private String desc_pTitre3;
    private String img_pTitre3;
    private String source;

    public  Model(){
    }

    public Model(Long stackId, Long time, String titre, String grdTitre, String auteur, String imagePrincipale, String desc, String ptitre1, String desc_pTitre1, String img_pTitre1, String ptitre2, String desc_pTitre2, String img_pTitre2, String ptitre3, String desc_pTitre3, String img_pTitre3, String source) {
        this.stackId = stackId;
        this.time = time;
        this.titre = titre;
        this.grdTitre = grdTitre;
        this.auteur = auteur;
        this.imagePrincipale = imagePrincipale;
        this.desc = desc;
        this.ptitre1 = ptitre1;
        this.desc_pTitre1 = desc_pTitre1;
        this.img_pTitre1 = img_pTitre1;
        this.ptitre2 = ptitre2;
        this.desc_pTitre2 = desc_pTitre2;
        this.img_pTitre2 = img_pTitre2;
        this.ptitre3 = ptitre3;
        this.desc_pTitre3 = desc_pTitre3;
        this.img_pTitre3 = img_pTitre3;
        this.source = source;
    }

    public Long getStackId() {
        return stackId;
    }

    public void setStackId(Long stackId) {
        this.stackId = stackId;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getGrdTitre() {
        return grdTitre;
    }

    public void setGrdTitre(String grdTitre) {
        this.grdTitre = grdTitre;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public String getImagePrincipale() {
        return imagePrincipale;
    }

    public void setImagePrincipale(String imagePrincipale) {
        this.imagePrincipale = imagePrincipale;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getptitre1() {
        return ptitre1;
    }

    public void setptitre1(String ptitre1) {
        this.ptitre1 = ptitre1;
    }

    public String getDesc_pTitre1() {
        return desc_pTitre1;
    }

    public void setDesc_pTitre1(String desc_pTitre1) {
        this.desc_pTitre1 = desc_pTitre1;
    }

    public String getImg_pTitre1() {
        return img_pTitre1;
    }

    public void setImg_pTitre1(String img_pTitre1) {
        this.img_pTitre1 = img_pTitre1;
    }

    public String getptitre2() {
        return ptitre2;
    }

    public void setptitre2(String ptitre2) {
        this.ptitre2 = ptitre2;
    }

    public String getDesc_pTitre2() {
        return desc_pTitre2;
    }

    public void setDesc_pTitre2(String desc_pTitre2) {
        this.desc_pTitre2 = desc_pTitre2;
    }

    public String getImg_pTitre2() {
        return img_pTitre2;
    }

    public void setImg_pTitre2(String img_pTitre2) {
        this.img_pTitre2 = img_pTitre2;
    }

    public String getptitre3() {
        return ptitre3;
    }

    public void setptitre3(String ptitre3) {
        this.ptitre3 = ptitre3;
    }

    public String getDesc_pTitre3() {
        return desc_pTitre3;
    }

    public void setDesc_pTitre3(String desc_pTitre3) {
        this.desc_pTitre3 = desc_pTitre3;
    }

    public String getImg_pTitre3() {
        return img_pTitre3;
    }

    public void setImg_pTitre3(String img_pTitre3) {
        this.img_pTitre3 = img_pTitre3;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}