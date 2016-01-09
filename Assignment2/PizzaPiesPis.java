/*
James Hahn
CS 0401
TA: Emilee Betz
Lab: M 11:00am-12:15pm
Lecture: M We 1:00-2:15pm

This is Pies, Pies, and Pis!
We sell plain and pepperoni pizza, slices of cherry pie, whole cherry pies, and 14k pi charms.
We also have Pi Cards, and if purchased, the customer will get several discounts on their order.

This program uses 3 functions (pieMemberBenefits, order, and main) to operate.
Where there is a chance for user input, I decided to go with while loops to check if the input was valid, and while the input is valid, just keep on asking the same question.
    This is a much better option than if statements because if statements are a one time thing and the user could enter input that is invalid twice in a row.
I tried my best to make it look aesthetically pleasing.

======================EXTRA CREDIT============================
-EXTRA CREDIT-    , *** = Did this extra credit
***Recommend adding a slice of pie if it'll save money
***Show the Pie Card savings for the purchase
=============================================================
*/

import java.util.Scanner;

public class PizzaPiesPis{
    public static void pieMemberBenefits(String answer){
        Scanner sc = new Scanner(System.in);

        System.out.println("\n*---------Your Available Discounts---------*");
        if(answer.equals("yes")){
            //PRINT OFFERS FOR ALL PIE CARD MEMBERS
            System.out.println("(1) Pepperoni Pizza for the price of Plain Pizza");
            System.out.println("(2) $0.25 off per Slice of Cherry Pie and $2.00 off per Whole Cherry Pie");
            System.out.println("(3) 10% off of every Pi Charm");
            System.out.println("(4) If the final order is more than $100.00 (after discounts), you get 10% off");
        } else if(answer.equals("no")){
            System.out.println("No available discounts at the moment.");
        }
    }

    public static int order(String pieCardMember){
        Scanner sc = new Scanner(System.in);

        //CHECK IF IT'S A CARD MEMBER FOR ORDER DISCOUNTS LATER ON
        boolean cardMember = false;
        if(pieCardMember.equals("yes")){
            cardMember = true;
        } else if(pieCardMember.equals("no")){
            cardMember = false;
        }

        //LIST OF PRICES
        double pricePlainPizza = 10.00;
        double pricePepperoniPizza = 12.00;
        double priceCherryPieSlice = 2.00;
        double priceCherryPieWhole = 10.00;
        double pricePiCharm = 50.00;
        if(cardMember){ //IF IT'S A CARD MEMBER, APPLY THE PRICE BENEFITS
            pricePepperoniPizza = 10.00;
            priceCherryPieSlice = 1.75;
            priceCherryPieWhole = 8.00;
            pricePiCharm = 45.00;
        }

        //QUANTITY OF EACH FOOD IN ORDER
        int pepperoniPizzas = 0;
        int plainPizzas = 0;
        int cherryPieSlices = 0;
        int cherryPieWholes = 0;
        int piCharms = 0;

        //INPUT FOR ORDER CHOICE AND VARIABLES FOR TOTAL + PAYMENT AT THE END
        int orderInput = 0;
        double total = 0.00;
        double subtotal = 0.00;
        double newSubtotal = 0.00;
        final double taxRate = .07;
        double tax = 0.00;
        double hundredDollarDiscount = 0.00;
        double paymentAmount = 0.00;
        double customerChange = 0.00;
        double savings = 0.00;
        String takeExtraSliceofPie = "";


        //PRINT OUT THE MENU
        System.out.println("*-------------------Menu-------------------*");
        System.out.println("Plain Pizza:                          $10.00");
        System.out.println("Pepperoni Pizza:                      $12.00");
        System.out.println("Slice of Cherry Pie:                  $ 2.00");
        System.out.println("Whole Cherry Pie (6 slices):          $10.00");
        System.out.println("Pi Charm (14k gold):                  $50.00");

        //TAKE THE ORDER
        while(orderInput != 4){
            System.out.println("*---------------Order Options--------------*");
            System.out.printf ("(1) Update Pizza quantity (%d plain and %d pepperoni)\n", plainPizzas, pepperoniPizzas);
            System.out.printf ("(2) Update Cherry Pie order (%d slices and %d entire pies)\n", cherryPieSlices, cherryPieWholes);
            System.out.printf ("(3) Update Pi Charm order (%d charms)\n", piCharms);
            System.out.println("(4) Checkout and Pay");
            System.out.println("*------------------------------------------*\n");

            System.out.print("What would you like to do? ");
            orderInput = sc.nextInt();
            while(orderInput < 1 || orderInput > 4){
                System.out.println("That's not a valid option. Please enter a correct choice.\n");
                System.out.print("What would you like to do? ");
                orderInput = sc.nextInt();
            }

            //START ASKING FOR THE QUANTITY OF EACH FOOD DEPENDING ON THE INPUT CHOICE
            if(orderInput == 1){
                System.out.printf("\nYou ordered %d plain and %d pepperoni pizzas.\n", plainPizzas, pepperoniPizzas);
                System.out.printf("How many plain pizzas ($10.00) would you like? ");
                plainPizzas = sc.nextInt();
                while(plainPizzas < 0){
                    System.out.println("You can't have less than 0 pizzas. Try again.\n");
                    System.out.printf("How many plain pizzas ($10.00) would you like? ");
                    plainPizzas = sc.nextInt();
                }

                System.out.printf("How many pepperoni pizzas ($%.2f) would you like? ", pricePepperoniPizza);
                pepperoniPizzas = sc.nextInt();
                while(pepperoniPizzas < 0){
                    System.out.println("You can't have less than 0 pizzas. Try again.\n");
                    System.out.printf("How many pepperoni pizzas ($%.2f) would you like? ", pricePepperoniPizza);
                    pepperoniPizzas = sc.nextInt();
                }
            } else if(orderInput == 2){
                System.out.printf("\nYou ordered %d slice(s) of cherry pie and %d whole cherry pie(s).\n", cherryPieSlices, cherryPieWholes);
                System.out.printf("How many slices of cherry pie (6 slices ($%.2f each) = 1 whole ($%.2f)) would you like? ", priceCherryPieSlice, priceCherryPieWhole);
                cherryPieSlices = sc.nextInt();
                while(cherryPieSlices < 0){
                    System.out.println("You can't have less than 0 slices of cherry pie. Try again.\n");
                    System.out.printf("How many slices of cherry pie (6 slices ($%.2f each) = 1 whole ($%.2f)) would you like? ", priceCherryPieSlice, priceCherryPieWhole);
                    cherryPieSlices = sc.nextInt();
                }

                //CONVERT SLICES INTO WHOLES, ANY REMAINDER USING THE MODULUS OPERATOR IS USED AS THE NUMBER OF SLICES
                cherryPieWholes = (cherryPieSlices - cherryPieSlices % 6)/6;
                cherryPieSlices = cherryPieSlices % 6;

                //NEXT IF AND ELSE CONDITIONALS ARE FOR EXTRA CREDIT: DETERMINE IF THE CUSTOMER WILL SAVE MONEY IF THEY ADD AN EXTRA SLICE OF CHERRY PIE
                if(cherryPieSlices*priceCherryPieSlice > priceCherryPieWhole){
                    System.out.print("\nIf you buy an extra slice of cherry pie, you'll save $0.75. Would you like to do this? ");
                    takeExtraSliceofPie = sc.next();
                    takeExtraSliceofPie = takeExtraSliceofPie.toLowerCase();
                    while(!takeExtraSliceofPie.equals("yes") && !takeExtraSliceofPie.equals("no")){
                        System.out.println("\nPlease answer the question correctly.");
                        System.out.print("Would you like to do this? ");
                        takeExtraSliceofPie = sc.next();
                        takeExtraSliceofPie = takeExtraSliceofPie.toLowerCase();
                    }

                    if(takeExtraSliceofPie.equals("yes")){
                        cherryPieSlices = 0;
                        cherryPieWholes++;
                        System.out.println("Added an extra slice.");
                    }
                    else if(takeExtraSliceofPie.equals("no")){
                        System.out.println("Ok.");
                    }
                }
                else if(cherryPieSlices*priceCherryPieSlice == priceCherryPieWhole){
                    System.out.print("\nIf you buy an extra slice of cherry pie, your total will not increase. Would you like to do this? ");
                    takeExtraSliceofPie = sc.next();
                    takeExtraSliceofPie = takeExtraSliceofPie.toLowerCase();
                    while(!takeExtraSliceofPie.equals("yes") && !takeExtraSliceofPie.equals("no")){
                        System.out.println("\nPlease answer the question correctly.");
                        System.out.print("Would you like to do this? ");
                        takeExtraSliceofPie = sc.next();
                        takeExtraSliceofPie = takeExtraSliceofPie.toLowerCase();
                    }

                    if(takeExtraSliceofPie.equals("yes")){
                        cherryPieSlices = 0;
                        cherryPieWholes++;
                        System.out.println("Added an extra slice.");
                    }
                    else if(takeExtraSliceofPie.equals("no")){
                        System.out.println("Ok.");
                    }
                }
            } else if(orderInput == 3){
                System.out.printf("\nYou ordered %d pi charms.\n", piCharms);
                System.out.printf("How many pi charms ($%.2f) would you like? ", pricePiCharm);
                piCharms = sc.nextInt();
                while(piCharms < 0){
                    System.out.println("You can't have less than 0 pi charms. Try again.\n");
                    System.out.printf("How many pi charms ($%.2f) would you like? ", pricePiCharm);
                    piCharms = sc.nextInt();
                }
            }
        }

        //CALCULATE THE SUBTOTAL
        subtotal = pepperoniPizzas*pricePepperoniPizza + plainPizzas*pricePlainPizza + cherryPieSlices*priceCherryPieSlice + cherryPieWholes*priceCherryPieWhole + piCharms*pricePiCharm;

        //IF THE CUSTOMER IS A CARD MEMBER, APPLY THE DISCOUNTS
        if(cardMember){
            if(subtotal >= 100.00){
                hundredDollarDiscount = subtotal*.10;
                newSubtotal = subtotal - hundredDollarDiscount;
            }

            savings = pepperoniPizzas*2 + piCharms*5 + cherryPieSlices*.25 + cherryPieWholes*2 + hundredDollarDiscount;
        }

        //IF THE CUSTOMER ORDERS NOTHING
        if((plainPizzas + pepperoniPizzas + cherryPieSlices + cherryPieWholes + piCharms) == 0){
            System.out.println("\nNo items were puchased. Thanks for stopping by anyway. Have a good day!");
            return 1;
        }

        //PRINT OUT RECEIPT
        System.out.println("\n------------------------Receipt-------------------------");
        System.out.println("      Item             | Number | Indiv. Price | Total");
        System.out.println("--------------------------------------------------------");
        //THE NEXT 5 LINES ARE TO MAKE SURE THAT THE FOOD ITEMS WILL ONLY PRINT OUT IF THERE IS MORE THAN 0 OF THEM ORDERED
        if(plainPizzas > 0)     System.out.printf("Plain Pizza            |   %2d   |    $%5.2f    | $%6.2f\n", plainPizzas, pricePlainPizza, plainPizzas*pricePlainPizza);
        if(pepperoniPizzas > 0) System.out.printf("Pepperoni Pizza        |   %2d   |    $%5.2f    | $%6.2f\n", pepperoniPizzas, pricePepperoniPizza, pepperoniPizzas*pricePepperoniPizza);
        if(cherryPieSlices > 0) System.out.printf("Slices of Cherry Pie   |   %2d   |    $%5.2f    | $%6.2f\n", cherryPieSlices, priceCherryPieSlice, cherryPieSlices*priceCherryPieSlice);
        if(cherryPieWholes > 0) System.out.printf("Whole Cherry Pies      |   %2d   |    $%5.2f    | $%6.2f\n", cherryPieWholes, priceCherryPieWhole, cherryPieWholes*priceCherryPieWhole);
        if(piCharms > 0)        System.out.printf("Pi Charms              |   %2d   |    $%5.2f    | $%6.2f\n", piCharms, pricePiCharm, piCharms*pricePiCharm);
        System.out.println("--------------------------------------------------------");
        System.out.printf("Subtotal:                                        $%6.2f \n", subtotal);
        //IF THE MEMBER IS A CARD MEMBER AND THE HUNDRED DOLLAR DISCOUNT APPLIES, THEN THERE HAS TO BE EXTRA STUFF ADDED TO THE RECEIPT
        if(cardMember && subtotal >= 100){
            tax = taxRate*newSubtotal;
            total = newSubtotal + tax;
            System.out.printf("10%% Discount:                                   -$%6.2f \n", hundredDollarDiscount);
            System.out.println("--------------------------------------------------------");
            System.out.printf("New Subtotal:                                    $%6.2f \n", newSubtotal);
            System.out.printf("Tax (7%%):                                        $%6.2f \n", tax);
            System.out.println("--------------------------------------------------------");
            System.out.printf("Total:                                           $%6.2f \n", total);
        } else{ //OR IF THE ABOVE DOESN'T APPLY, JUST ADD TAX AND CALCULATE THE TOTAL
            tax = taxRate*subtotal;
            total = subtotal + tax;
            System.out.printf("Tax (7%%):                                        $%6.2f \n", tax);
            System.out.println("--------------------------------------------------------");
            System.out.printf("Total:                                           $%6.2f \n", total);
        }

        if(cardMember){
            System.out.printf("Your Pie Card savings today (before taxes):      $%6.2f \n", savings);
        }

        //ASK FOR PAYMENT
        System.out.print("Payment Amount:                                  $");
        paymentAmount = sc.nextDouble();
        while(paymentAmount < total){
            System.out.println("\nIn order to leave, you must pay at least the amount of your total.\n");
            System.out.print("Payment Amount:                                  $");
            paymentAmount = sc.nextDouble();
        }
        customerChange = paymentAmount - total;
        System.out.printf ("\nYour Change:                                     $%6.2f", customerChange);
        System.out.println("\n\nThank you for ordering from Pies, Pies, and Pis! Have a good day!\n\n");

        return 1;
    }

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to Pies, Pies, and Pis!");
        boolean customerInLine = true;
        String customerWaiting = "";
        String pieCardMember = "";

        //WHILE THERE ARE CUSTOMERS IN THE STORE WAITING TO ORDER
        while(customerInLine){
            System.out.print("\nIs there a customer in line (yes or no)? ");
            customerWaiting = sc.next();
            customerWaiting = customerWaiting.toLowerCase(); //MAKING IT LOWERCASE SO IT ACCEPTS "YeS" and "yEs", THIS IS A GOOD THING

            if(customerWaiting.equals("yes")){
                System.out.print("\nAre you a Pie Card member (yes or no)? ");
                pieCardMember = sc.next();
                pieCardMember = pieCardMember.toLowerCase();
                while(!pieCardMember.equals("yes") && !pieCardMember.equals("no")){
                    System.out.print("\nAre you a Pie Card member (yes or no)? ");
                    pieCardMember = sc.next();
                    pieCardMember = pieCardMember.toLowerCase();
                    if(!pieCardMember.equals("yes") && !pieCardMember.equals("no")){
                        System.out.println("Invalid answer. Please enter 'yes' or 'no'.");
                    }
                }

                //USE THESE 2 FUNCTIONS TO DISPLAY PIE CARD MEMBER BENEFITS AND THEN GO THROUGH THE ENTIRE ORDER PROCESS
                pieMemberBenefits(pieCardMember);
                order(pieCardMember);
            } else if(customerWaiting.equals("no")){
                customerInLine = false;
            } else{
                System.out.println("You did not answer with yes or no. Try again.");
            }
        }
    }
}
