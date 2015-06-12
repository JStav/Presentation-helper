package edu.ucf.cecs.acm.presentationhelper;

/**
 * Created by kishoredebnath on 01/04/15.
 */
public class Presentation_Structure {

    //Static data to all the slide objects
    private static int slideId;
    private static int totalDuration, totalSlides, maxDuration = 2;
    public static Presentation_Structure presentation = null;
    public static boolean customTimeShared = false;

    private int sId;
    private int currentDuration;
    //public int totalSlides;
    public Presentation_Structure nextSlide;


    //Constructor per slide - slide builder
    private Presentation_Structure(int duration, int slideId){

        this.currentDuration = duration;
        this.sId = slideId;
        this.nextSlide = null;
    }

    //Create Presentation for the given number of slides each with time: duration
    private static void createPresentation(int duration){
        if(Presentation_Structure.totalSlides>0) {
            slideId = 1;
            if(presentation == null) {
                presentation = new Presentation_Structure(duration, slideId);
            }else {
                presentation.currentDuration = duration;
            }

            Presentation_Structure current = presentation;

            for(int i = 1; i<Presentation_Structure.totalSlides; i++){
                slideId++;
                if(current.nextSlide == null) {
                    current.nextSlide = new Presentation_Structure(duration, slideId);
                }else{
                    current.nextSlide.currentDuration = duration;
                }
                current = current.nextSlide;
            }
            current.nextSlide = null;
            maxDuration = duration;
        }
    }

    //Returns the Singleton head pointer pointing to the slides of the presentations
    public static Presentation_Structure getPresentation(int totalDuration, int totalSlides){

        int slideDuration = 0;

        if(presentation == null){

            Presentation_Structure.totalDuration = totalDuration;
            Presentation_Structure.totalSlides = totalSlides;
            slideDuration = totalDuration/totalSlides;

            createPresentation(slideDuration);

            return presentation;

        }else{
            //return the same presentation object if the duration and number of slides are same
            if(totalDuration == Presentation_Structure.totalDuration && totalSlides==Presentation_Structure.totalSlides && !Presentation_Structure.customTimeShared){


                return presentation;

            }else{

                //update the duration or slide accordingly and return the presentation object

                Presentation_Structure.totalDuration = totalDuration;
                Presentation_Structure.totalSlides = totalSlides;
                slideDuration = totalDuration/totalSlides;

                Presentation_Structure.customTimeShared = false;
                createPresentation(slideDuration);

                return presentation;

            }

        }

    }

    public int slideId(){
        return this.sId;
    }

    public int getDuration(){
        return this.currentDuration;
    }

    public void setDuration(int duration){

        if(!Presentation_Structure.customTimeShared) {
            Presentation_Structure.customTimeShared = true;
        }

        Presentation_Structure.totalDuration += duration-this.currentDuration;

        if(duration-this.currentDuration>0&&Presentation_Structure.maxDuration>duration){
            Presentation_Structure.maxDuration = duration;
        }

        this.currentDuration = duration;

    }

    public static int getTotalDuration(){return Presentation_Structure.totalDuration;}

    public static int getTotalSlides(){return Presentation_Structure.totalSlides;}

    public static int getMaxDuration(){return Presentation_Structure.maxDuration;}

}
