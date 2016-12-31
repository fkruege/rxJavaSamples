/**
 * Created by fkruege on 3/26/16.
 */
public class Main {

    public static void main(String[] args) {

        if (args.length >= 1) {

            System.out.println("Running command: " + args[0]);

            switch (args[0]) {
                case "1":
                    RxJava1.run();
                    return;
                case "2":
                    RxJava2.run();
                    return;
            }

        }

//        RxJava8_MoreErrors test = new RxJava8_MoreErrors();
        RxJava8_MoreErrors.run();


    }


}
