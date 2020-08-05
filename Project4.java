import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.*;

public class Project4{
   public static void main(String[] args) {

        int y=30;

        NavigableMap<Integer,Property> records= new TreeMap<Integer,Property>();

        int label_x=10;//the x coordinate for the label

        int text_x=140;//the x coordinate for texarea or input container

        int label_width=120;

        int label_height=30;

        int text_width=200;

        int text_height=30;

        JFrame f= new JFrame("TextField Example");

        JTextField textAddress,textTransaction,textBedroom,textSquare,textPrice;

        JLabel labelAddress,labelTransaction,labelBedroom,labelSquare,labelPrice;

        labelTransaction= new JLabel("Transaction No.");

        labelTransaction.setBounds(label_x,y,label_width,label_height);

        textTransaction= new JTextField(" ");

        textTransaction.setBounds(text_x,y,text_width,text_height);

        labelAddress= new JLabel("Address");

        labelAddress.setBounds(label_x,y+40,label_width,label_height);

        textAddress= new JTextField(" ");

        textAddress.setBounds(text_x,y+40, text_width,text_height);

        labelBedroom = new JLabel("Bedrooms");

        labelBedroom.setBounds(label_x,y+80,label_width,label_height);

        textBedroom= new JTextField(" ");

        textBedroom.setBounds(text_x,y+80,text_width,text_height);

        labelSquare= new JLabel("Square Footage");

        labelSquare.setBounds(label_x,y+120,label_width,label_height);

        textSquare = new JTextField(" ");

        textSquare.setBounds(text_x,y+120,text_width,text_height);

        labelPrice = new JLabel("Price");

        labelPrice.setBounds(label_x,y+160,label_width,label_height);

        textPrice= new JTextField(" ");

        textPrice.setBounds(text_x,y+160,text_width,text_height);

        f.add(labelTransaction); f.add(textTransaction);

        f.add(labelAddress); f.add(textAddress);

        f.add(labelBedroom); f.add(textBedroom);

        f.add(labelSquare); f.add(textSquare);

        f.add(labelPrice);f.add(textPrice);

        String actions[]={"Insert","Update","Find"};

        JComboBox comboActions=new JComboBox(actions);

        comboActions.setBounds(text_x+100,y+200, 100,35);

        f.add(comboActions);

        JButton buttonProcess=new JButton("Process");

        buttonProcess.setBounds(10,y+200,120,40);

        JButton buttonStatus=new JButton("Change Status");

        buttonStatus.setBounds(10,y+250,120,40);

        String status[]={"FOR_SALE","UNDER_CONTRACT","SOLD"};

        JComboBox comboStatus=new JComboBox(status);

        comboStatus.setBounds(text_x+100,y+250, 100,35);

        f.add(buttonProcess);f.add(buttonStatus);f.add(comboStatus);

        f.setSize(400,400);

        f.setLayout(null);

        buttonProcess.addActionListener(new ActionListener() { /**Added "ears" to button; so can "listen" for something to do*/

        @Override
        public void actionPerformed(ActionEvent e) { /**Method when listen to action -> what action want to perform?*/
            // TODO Auto-generated method stub

            int status=1,bedrooms=0,footage=0,price=0,transaction=0;
            String response=" ";
             if(!textPrice.getText().equals(" ")){/**checking if the texts values are empty and numeric format */
                price=getValueInteger(textPrice);
             }
             if(!textBedroom.getText().equals(" ")){
                 bedrooms=getValueInteger(textBedroom);
             }
             if(!textSquare.getText().equals(" ")){
               footage=getValueInteger(textSquare);
             }
             if(!textAddress.getText().equals(" ")){
                 status=0;
             }
             if(textTransaction.getText().equals(" ")){
                  status=0;
             }else{
                 transaction=getValueInteger(textTransaction);
             }

             if(transaction !=0){
                 Property newProperty= new Property(textTransaction.getText().trim(),textAddress.getText().trim(),bedrooms,footage,price);


                 for(Status state:Status.values()){
                     if(String.valueOf(state)==comboStatus.getSelectedItem())
                         newProperty.changeState(state);
                 }



                 if(String.valueOf(comboActions.getSelectedItem()) =="Insert"){
                     if(records.get(transaction) !=null){
                         JOptionPane.showMessageDialog(null, "Already Exists in the Database");
                     }
                     records.put(transaction,newProperty);
                 }else if(String.valueOf(comboActions.getSelectedItem()) =="Update"){
                    if(records.replace(transaction,newProperty) == null){
                        JOptionPane.showMessageDialog(null, "No record found");
                    }else{
                        records.replace(transaction,newProperty);
                    }
                 }else if(String.valueOf(comboActions.getSelectedItem()) == "delete"){
                    records.remove(transaction);
                 }else if(String.valueOf(comboActions.getSelectedItem()) =="Find"){
                   if(!records.containsKey(transaction)){
                       JOptionPane.showMessageDialog(null, "No record found");
                   }else{
                       Property current = records.get(transaction);

                       textTransaction.setText(current.transaction);
                       textAddress.setText(current.address);
                       textPrice.setText(String.valueOf(current.price));
                       textBedroom.setText(String.valueOf(current.bedrooms));
                       textSquare.setText(String.valueOf(current.footage));


                   }
                 }
             }

             System.out.println(records.size());

            }
        });


        f.setVisible(true);
    }
    public static int getValueInteger(JTextField textField){
        int value=0;
        try{
            value=Integer.valueOf(textField.getText().trim());
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Non numeric textfield");
        }
        return value;
    }
}
interface StateChangeable <E>{
    public String property="";
    public String address="";
    public int bedrooms=0;
    public int footage=0;
    public int price=0;
    public String Status="FOR_SALE";
    public void changeState(E State);

}
class Property implements StateChangeable<Status>{
    public String transaction="";
    public String address="";
    public int bedrooms=0;
    public int footage=0;
    public int price=0;
    public Status status;

    @java.lang.Override
    public void changeState(Status State) {
        this.status=State;
    }

    public Property(String trans,String address,int bedrooms,int Square,int price){
        this.address=address;
        this.transaction=trans;
        this.bedrooms=bedrooms;
        this.footage=Square;
        this.price=price;
    }
    public String toString(){

        String concatResult="Address:"+this.address+" , Bedrooms: "+String.valueOf(this.bedrooms)+", Square Footage:"+String.valueOf(this.footage)+" ,Price: "+String.valueOf(this.price)+", Current Status : "+this.Status;

        return concatResult;
    }
}

enum Status{
    FOR_SALE,
    UNDER_CONTRACT
    ,SOLD;
}