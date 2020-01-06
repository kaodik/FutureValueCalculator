package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.text.NumberFormat;
import java.text.DecimalFormat;
import java.util.Locale;
import java.util.Currency;

public class Main extends Application {
    Label lblPAmt;
    Label lblRateofReturn;
    Label lblYrsInvested;
    Label lblResults;
    TextField txtPAmt;
    TextField txtRateofReturn;
    TextField txtYrsInvested;
    RadioButton radAnn;
    RadioButton radMonth;
    RadioButton radWeek;
    RadioButton radDay;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Future Value Calculator");

        Image tree = new Image("file:moneytree.jpg");
        ImageView imgViewMTree = new ImageView(tree);
        imgViewMTree.setFitWidth(250);
        imgViewMTree.setPreserveRatio(true);
        HBox picBox = new HBox(imgViewMTree);

        //userInput Section
        lblPAmt = new Label("Enter principal amount:");
        txtPAmt = new TextField();
        lblRateofReturn = new Label("Enter expected rate of return(e.g., 8 for 8%):");
        txtRateofReturn = new TextField();
        lblYrsInvested = new Label("Enter number of years money will be invested:");
        txtYrsInvested = new TextField();

        GridPane usrInputGrid = new GridPane();

        usrInputGrid.add(lblPAmt, 0, 0);
        usrInputGrid.add(txtPAmt, 1, 0);
        usrInputGrid.add(lblRateofReturn, 0, 1);
        usrInputGrid.add(txtRateofReturn, 1, 1);
        usrInputGrid.add(lblYrsInvested, 0, 2);
        usrInputGrid.add(txtYrsInvested, 1, 2);

        usrInputGrid.setHgap(50);
        usrInputGrid.setVgap(10);
        usrInputGrid.setPadding(new Insets(20));

        //radGroup Section
        radAnn = new RadioButton("Annually");
        radMonth = new RadioButton("Monthly");
        radWeek = new RadioButton("Weekly");
        radDay = new RadioButton("Daily");
        ToggleGroup togGrpMoneyInput = new ToggleGroup();
        radAnn.setToggleGroup(togGrpMoneyInput);
        radMonth.setToggleGroup(togGrpMoneyInput);
        radWeek.setToggleGroup(togGrpMoneyInput);
        radDay.setToggleGroup(togGrpMoneyInput);

        HBox hBoxMoneyInput = new HBox(50, this.radAnn, this.radMonth, this.radWeek, this.radDay);
        hBoxMoneyInput.setAlignment(Pos.CENTER);

        TitledPane MoneyInputPane = new TitledPane("How frequently will the interest be compounded?", hBoxMoneyInput);
        MoneyInputPane.setCollapsible(false);

        lblResults = new Label("");
        lblResults.setMinHeight(35);

        VBox vBoxResults = new VBox(5,lblResults);
        Button btnCalc = new Button("Calculate");
        Button btnClear = new Button("Clear");
        Button btnExit = new Button("Exit");

        btnCalc.setMinWidth(150);
        btnCalc.setMinHeight(50);
        btnClear.setMinWidth(150);
        btnClear.setMinHeight(50);
        btnExit.setMinWidth(150);
        btnExit.setMinHeight(50);

        btnCalc.setOnAction( new btnGetCalcClickHandler());

        btnClear.setOnAction(new btnClearClickHandler());

        btnExit.setOnAction(new btnExitClickHandler());
        //positioning the btns
        HBox buttonBox = new HBox(10, btnCalc, btnClear, btnExit);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(20));

        //combinding the all together
        VBox vBoxleft = new VBox(5, usrInputGrid,MoneyInputPane, vBoxResults,buttonBox);
        HBox hBoxAddingPic = new HBox(5,vBoxleft, picBox);
        hBoxAddingPic.setPadding(new Insets(20));
        root = hBoxAddingPic;

        primaryStage.setScene(new Scene(root, 800, 400));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    class btnExitClickHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            System.exit(0);
        }

    }
    class btnClearClickHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            txtYrsInvested.setText("");
            txtPAmt.setText("");
            txtRateofReturn.setText("");
            lblResults.setText("");
            radAnn.setSelected(false);
            radDay.setSelected(false);
            radMonth.setSelected(false);
            radWeek.setSelected(false);


        }

    }

    class btnGetCalcClickHandler implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent event){
            double pamt = Double.parseDouble(txtPAmt.getText());
            double rateofReturn = Double.parseDouble(txtRateofReturn.getText());
            int YrsInvested = Integer.parseInt(txtYrsInvested.getText());
            int compInterest = 0;

            if(radAnn.isSelected())
                compInterest = 1;
            else if (radMonth.isSelected())
                compInterest = 12;
            else if (radWeek.isSelected())
                compInterest = 52;
            else if (radDay.isSelected())
                compInterest = 360;
            else
                compInterest = 0;

            double fvAmt = pamt * Math.pow((1 + (rateofReturn/100)/compInterest), compInterest*YrsInvested);

            NumberFormat nf = NumberFormat.getCurrencyInstance();
            DecimalFormat df = new DecimalFormat("##");

            String Result = "In " + txtYrsInvested.getText() + " years, your initial investment" +
                    " of $" + df.format(pamt) + " will be will be worth "+nf.format(fvAmt) +
                    "\nStart investing now. Future you will thank you.";

            lblResults.setText(Result);
        }
    }
}
