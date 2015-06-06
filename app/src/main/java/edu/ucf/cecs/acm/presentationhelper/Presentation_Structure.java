package edu.ucf.cecs.acm.presentationhelper;

/**
 * Created by kishoredebnath on 01/04/15.
 */
public class Presentation_Structure {
    private int minDuration = 2;
    private boolean head;
    private boolean customDuration;
    private boolean recentEdit;
    private int currentSlide;
    private int currentDuration;
    public int totalSlides;
    public int maxDuration = minDuration;
    public Presentation_Structure nextSlide;
    private Presentation_Structure(int totalSlides, int currentDuration){
        this.head = this.recentEdit = false;
        this.totalSlides = totalSlides;
        this.currentDuration = currentDuration;
        this.customDuration = false;
        this.maxDuration = currentDuration;
    }

    public static Presentation_Structure createPresentation(int totalSlides, int totalDuration){

        Presentation_Structure headSlides = null,
                                previousSlide = null;

        int slides = totalSlides,
            eachDuration = 0;
        if(totalSlides>0) {
            eachDuration = totalDuration / totalSlides;
            do {
                if(headSlides == null){
                    headSlides = new Presentation_Structure(totalSlides, eachDuration);
                    headSlides.head = true;
                    headSlides.currentSlide = 1;
                    previousSlide = headSlides;
                }else {
                    previousSlide.nextSlide = new Presentation_Structure(totalSlides, eachDuration);
                    previousSlide.nextSlide.currentSlide = previousSlide.currentSlide+1;
                    previousSlide = previousSlide.nextSlide;
                }
            }while(--slides>0);
            previousSlide.nextSlide = null;
        }
        return headSlides;
    }

    public void editPresentation(int slideIdentity, int editDuration){
        Presentation_Structure current = this;

        int changedDuration = 0;

        while(current.currentSlide!=slideIdentity && current!=null){
            current = current.nextSlide;
        }

        changedDuration = editDuration - current.currentDuration ;
        current.currentDuration = editDuration;
        current.recentEdit = true;

        if(current.currentDuration > this.maxDuration){
            this.maxDuration = currentDuration;
        }

        current = this;

        while(changedDuration!=0&&current!=null){
            if (current.recentEdit == true) {
                current = current.nextSlide;
                continue;
            }
            if (current.recentEdit ==  false){
                if(current.currentDuration - changedDuration > minDuration){
                    current.currentDuration =- changedDuration;
                    if(current.currentDuration > this.maxDuration){
                        this.maxDuration = currentDuration;
                    }
                    changedDuration = 0;
                    break;
                }else{
                    changedDuration = current.currentDuration - minDuration;
                    current.currentDuration = minDuration;
                    current = current.nextSlide;
                }
            }
        }
    }

    public int slideId(){
        return this.currentSlide;
    }

    public int getDuration(){
        return this.currentDuration;
    }

    public void setDuration(int x){this.currentDuration = x;}
}
