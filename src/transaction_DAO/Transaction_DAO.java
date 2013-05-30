package transaction_DAO;


import java.sql.SQLException;
import java.util.ArrayList;

import bean.reportBean;
import bean.transactionBean;
import beans.Beanstransaction_record;
import beans.Brgy;
import beans.Month_Year;
import beans.Municpality;
import beans.BeansAdd;
import beans.Beanstransaction;

import myconnection.ConnectionDAO;

public class Transaction_DAO extends ConnectionDAO{

	public Transaction_DAO() throws SQLException {
		super();
		// TODO Auto-generated constructor stub
	}
	//searching for household_id
	public ArrayList<Beanstransaction>household_list(String val ,int condition){
		ArrayList<Beanstransaction>list=new ArrayList<Beanstransaction>();
		Beanstransaction beans=null;
		try{
			if(condition==1){
				rs=con.createStatement().executeQuery("select household_id,household_member_id,head_name,barangay,municipality from household_tbl " +
												"where household_id like '%"+val+"%' order by rand(), head_name limit 50 ");
			}
			else{
				rs=con.createStatement().executeQuery("select household_id,household_member_id,head_name,barangay,municipality from household_tbl " +
						"where household_id like '%"+val+"%' order by head_name");
			}
			while(rs.next()){
				beans = new Beanstransaction(rs.getString("household_id"),
						rs.getInt("household_member_id"), 
						rs.getString("head_name"),
						rs.getString("barangay"),
						rs.getString("municipality"));
				list.add(beans);
			}
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}
	public ArrayList<transactionBean> getServers(){
		ArrayList<transactionBean>list=new ArrayList<transactionBean>();
		transactionBean beans=null;
		try{
				rs=con.createStatement().executeQuery("select * from servers_tbl ");
			while(rs.next()){
				beans = new transactionBean(rs.getInt("id"),
						rs.getString("name"),
						rs.getString("description"));
				list.add(beans);
			}
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}
	
	
	//searching for head_name
	public ArrayList<Beanstransaction>granteelist(String name){
		ArrayList<Beanstransaction>list=new ArrayList<Beanstransaction>();
		Beanstransaction beans=null;
		try{
			rs=con.createStatement().executeQuery("select household_id,household_member_id,head_name,barangay,municipality from household_tbl " +
												"where head_name like '%"+name+"%' order by head_name ");
			while(rs.next()){
				beans=new Beanstransaction(rs.getString("household_id"),
						rs.getInt("household_member_id"),
						rs.getString("head_name"),
						rs.getString("barangay"),
						rs.getString("municipality"));
				list.add(beans);
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}
	
	
	//searching for spouse/wife_name
	public ArrayList<Beanstransaction>wifelist(String name){
		ArrayList<Beanstransaction>list = new ArrayList<Beanstransaction>();
		Beanstransaction beans=null;
		try{
			rs=con.createStatement().executeQuery("select h.household_id,w.household_member_id,h.head_name,spouse_name,barangay,municipality from wife_tbl as w ,household_tbl as h "+
					"where h.household_id=w.household_id and spouse_name like '%"+name+"%' order by h.head_name ");
			while(rs.next()){
				beans=new Beanstransaction(rs.getString("household_id"),
						rs.getInt("household_member_id"), 
						rs.getString("head_name"),
						rs.getString("spouse_name"),
						rs.getString("barangay"),
						rs.getString("municipality"));
				list.add(beans);
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}
	
	
	//searching for student
	public ArrayList<Beanstransaction>studentlist(String name){
		ArrayList<Beanstransaction>list=new ArrayList<Beanstransaction>();
		Beanstransaction beans=null;
		try{
			rs=con.createStatement().executeQuery("select h.household_id,c.household_member_id,h.head_name,child_name,barangay,municipality from children_tbl as c,household_tbl as h " +
					"where h.household_id=c.household_id and (c.attending_school=1 or h.attending_school=1) and child_name like '%"+name+"%' union "+
					"(select h.household_id,w.household_member_id,h.head_name,spouse_name,barangay,municipality from wife_tbl as w,household_tbl as h " +
					"where h.household_id=w.household_id and (w.attending_school=1 or h.attending_school=1) and spouse_name like '%"+name+"%')  union " +
					"(select h.household_id,h.household_member_id,head_name,w.spouse_name,barangay,municipality from household_tbl as h,wife_tbl as w " +
					"where h.household_id=w.household_id and (h.attending_school=1 or w.attending_school=1) and head_name like '%"+name+"%')  union " +
					"(select h.household_id,g.household_member_id,h.head_name,grandchild_name,barangay,municipality from grandchild_tbl as g,household_tbl as h " +
					"where h.household_id=g.household_id and (h.attending_school=1 or g.attending_school=1) and grandchild_name like '%"+name+"%')order by head_name");
			while(rs.next()){
				beans=new Beanstransaction(rs.getString("household_id"), 
						rs.getInt("household_member_id"), 
						rs.getString("head_name"),
						rs.getString("child_name"),
						rs.getString("barangay"),
						rs.getString("municipality"));
				list.add(beans);
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}
	
	//inserting a value in received_tbl
	public void addtransaction(BeansAdd beans){
		try{
			stmt=con.prepareStatement("insert into received_tbl values(?,?,?,?,?,?) ");
			stmt.setString(1, beans.getHousehold_id());
			stmt.setString(2, beans.getMonth());
			stmt.setString(3, beans.getDay());
			stmt.setString(4, beans.getTime());
			stmt.setFloat(5, beans.getAmount());
			stmt.setInt(6, beans.getReceive());
			int ret=stmt.executeUpdate();
			if(ret!=1)
				throw new SQLException("Failed to Add");
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	//searching for head_name where household_id are equal
	public boolean checkrecieve_list(String val){
		boolean check=false;
		try{
			String sql="select h.household_id, reason,month_and_year,receive from received_tbl as r ,household_tbl as h " +
					"where h.household_id = r.household_id and head_name like '%"+val+"%' ";
			rs=con.createStatement().executeQuery(sql);
			System.out.println(val);
			while(rs.next()){
				check=true;
			}
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return check;
	}
	
	//list of municipality in household_tbl
	public ArrayList<Municpality>municipal_list(){
		ArrayList<Municpality>list =new ArrayList<Municpality>();
		Municpality bean=null;
		try{
			rs=con.createStatement().executeQuery("select distinct m.mun_id,m.mun_name from household_tbl as h,municipal_tbl as m where h.municipality = m.mun_id order by h.municipality");
			while(rs.next()){
				bean=new Municpality(rs.getInt(1),rs.getString(2));
				list.add(bean);
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}
	
	//list of barangay where municipality are equal
	public ArrayList<Brgy>brgy_list(String municipal){
		ArrayList<Brgy>list=new ArrayList<Brgy>();
		Brgy bean=null;
		try{
			rs=con.createStatement().executeQuery("select distinct b.brgy_id,b.brgy_name from household_tbl as h,brgy_tbl as b where h.barangay = b.brgy_id and h.municipality = b.mun_id and b.mun_id="+municipal+" order by h.barangay");
			while(rs.next()){
				bean=new Brgy(rs.getInt(1),rs.getString(2));
				//System.out.println(rs.getInt(1)+rs.getString(2));
				list.add(bean);
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}
	
	//searching for municipality,barangay and head_name
	public ArrayList<Beanstransaction>municipality_list(String municipal,String brgy,String name){
		ArrayList<Beanstransaction>list=new ArrayList<Beanstransaction>();
		Beanstransaction beans=null;
		try{
			rs=con.createStatement().executeQuery("select household_id,household_member_id,head_name,barangay,municipality from household_tbl " +
					"where municipality like '%"+municipal+"%' and barangay='"+brgy+"' and head_name like '%"+name+"%' order by head_name");
			while(rs.next()){
				beans=new Beanstransaction(rs.getString("household_id"),
						rs.getInt("household_member_id"),
						rs.getString("head_name"),
						rs.getString("barangay"),
						rs.getString("municipality"));
				list.add(beans);
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}
	
	//searching for municipality and head_name
	public ArrayList<Beanstransaction>municipal_list1(String municipal,String name){
		ArrayList<Beanstransaction>list=new ArrayList<Beanstransaction>();
		Beanstransaction beans=null;
		try{
			rs=con.createStatement().executeQuery("select household_id,household_member_id,head_name,barangay,municipality from household_tbl " +
					"where municipality like '%"+municipal+"%' order by head_name ");
			while(rs.next()){
				beans=new Beanstransaction(rs.getString("household_id"),
						rs.getInt("household_member_id"),
						rs.getString("head_name"),
						rs.getString("barangay"),
						rs.getString("municipality"));
				list.add(beans);
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}
	
	//searching for barangay
	public ArrayList<Beanstransaction>brgay_list(String name){
		ArrayList<Beanstransaction>list=new ArrayList<Beanstransaction>();
		Beanstransaction beans=null;
		try{
			rs=con.createStatement().executeQuery("select household_id, household_member_id,head_name,barangay,municipality from household_tbl " +
					"where barangay = "+name+" order by head_name ");
			while(rs.next()){
				beans = new Beanstransaction(rs.getString("household_id"), 
						rs.getInt("household_member_id"), 
						rs.getString("head_name"),
						rs.getString("barangay"),
						rs.getString("municipality"));
				System.out.println(rs.getString("household_id")+
						rs.getInt("household_member_id")+
						rs.getString("head_name")+
						rs.getString("barangay")+
						rs.getString("municipality"));
				list.add(beans);
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}
	
	//inserting a value in reason_tbl
	/*public void addreason(BeansAdd beans){
		try{
			stmt=con.prepareStatement("insert into reason_tbl values(?,?,?,?,?)");
			stmt.setInt(1, beans.getReason_id());
			stmt.setString(2, beans.getHousehold_id());
			stmt.setString(3, beans.getReason());
			stmt.setInt(4, beans.getMonth());
			stmt.setInt(5, beans.getYear());
			int ret=stmt.executeUpdate();
			if(ret!=1)
				throw new SQLException("Failed to Add");
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}*/
	
	//list of received_tbl
	/*public ArrayList<BeansAdd>view_transaction(String id){
		ArrayList<BeansAdd>list=new ArrayList<BeansAdd>();
		BeansAdd beans=null;
		try{
			rs =con.createStatement().executeQuery("select sub from received_tbl where household_id = '"+id+"'");
			while(rs.next()){

				rs =con.createStatement().executeQuery("select sub from received_tbl where household_id = '"+id+"'");
				while(rs.next()){
					if(rs.getInt(1) == 0){
						System.out.println("0");
						rs=con.createStatement().executeQuery("select household_id,month_and_year,date_receive,time,amount,receive,comment,sub from received_tbl where household_id='"+id+"' order by receive ");
						while(rs.next()){
							beans=new BeansAdd(rs.getString("household_id"),
									rs.getString("month_and_year"),
									rs.getString("date_receive"),
									rs.getString("time"),
									rs.getFloat("amount"),
									rs.getInt("receive"),
									rs.getString("comment"),
									rs.getInt("sub"),
									"");
							list.add(beans);
						}
					}
					else{
						System.out.println("1");
						rs=con.createStatement().executeQuery("select household_id,month_and_year,date_receive,time,amount,receive,comment,sub,fname,lname from received_tbl,user_tbl where household_id='"+id+"' and sub = id order by receive ");
						while(rs.next()){
							String munLink_name = rs.getString("fname")+" "+rs.getString("lname");
							System.out.println("bean:"+munLink_name);
							beans=new BeansAdd(rs.getString("household_id"),
									rs.getString("month_and_year"),
									rs.getString("date_receive"),
									rs.getString("time"),
									rs.getFloat("amount"),
									rs.getInt("receive"),
									rs.getString("comment"),
									rs.getInt("sub"),
									munLink_name);
							list.add(beans);
						}
					}
				}
				
				
			
			}
			
		}catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}*/
	
	//searching for household_id only view the head_name,barangay and municipality
	public ArrayList<Beanstransaction>lists(String id){
		ArrayList<Beanstransaction>list=new ArrayList<Beanstransaction>();
		Beanstransaction beans=null;
		try{
			rs=con.createStatement().executeQuery("select head_name,brgy_name ,mun_name from household_tbl as h, municipal_tbl as m, brgy_tbl as b where b.brgy_id = h.barangay and m.mun_id = h.municipality " +
					"and household_id='"+id+"'");
			while(rs.next()){
				beans=new Beanstransaction(rs.getString("head_name"),
						rs.getString("brgy_name"),
						rs.getString("mun_name"));
				list.add(beans);
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}
	
	//searching for household_id,month,year in received_tbl
	public boolean check_month_year(String household_id,String month){
		boolean check=false;
		try{
			rs=con.createStatement().executeQuery("select month_and_year from received_tbl where household_id='"+household_id+"' and month_and_year="+month+" ");
			while(rs.next()){
				check=true;
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
		return check;
	}
	//checking for reason_id in reason_tbl
	/*public boolean check_reason_id(int reason_id){
		boolean check=false;
		try{
			rs=con.createStatement().executeQuery("select reason_id from reason_tbl where reason_id="+reason_id+" ");
			while(rs.next()){
				check=true;
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
		return check;
	}*/
	
	//implementation for reason_id
	/*public int reason_id(){
		int id=0;
		try{
			rs=con.createStatement().executeQuery("select count(reason_id) from reason_tbl");
			while(rs.next()){
				id=rs.getInt("reason_id")+1;
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
		return id;
	}*/
	
	//UPDATE TRANSACTION
	public void updatetransaction(String household_id,String month,float amount,int nwmonth){
		
		//if(recieve==1){
			/*try{
				con.createStatement().execute("delete from reason_tbl where household_id='"+household_id+"' and month="+month+" and year="+year+" ");
			}catch (Exception e) {
				// TODO: handle exception
			}*/
			try{
				con.createStatement().execute("update received_tbl set month_and_year="+nwmonth+",amount="+amount+" where household_id='"+household_id+"' and month_and_year="+month+" ");
			}catch (Exception e) {
				// TODO: handle exception
			}
			
		/*}else if(recieve==3){
			try{
				con.createStatement().execute("delete from reason_tbl where household_id='"+household_id+"' and month="+month+" and year="+year+" ");
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			try{
				con.createStatement().execute("update received_tbl set month_and_year="+nwmonth+",amount="+amount+",receive="+recieve+" where household_id='"+household_id+"' and month_and_year="+month+" ");
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}*/
	}
	
	//checking for updating a data
	public boolean check_updatetransaction(String household_id,int month,float amount,boolean recieve,int nwmonth){
		boolean check=false;
		if(recieve==true){
			try{
				con.createStatement().execute("update received_tbl set month_and_year="+nwmonth+",amount="+amount+",receive="+recieve+" where household_id='"+household_id+"' and month_and_year="+month+" ");
				check=true;
			}catch (Exception e) {
				// TODO: handle exception
			}
		}else if(recieve==false){
			try{
				con.createStatement().execute("update received_tbl set month_and_year="+nwmonth+",amount="+amount+",receive="+recieve+" where household_id='"+household_id+"' and month_and_year="+month+" ");
				check=true;
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return check;
	}
	
	//searching for household_id only view the head_name in household_tbl
	public String head_name(String household_id){
	String head_name=null;
	try{
		rs=con.createStatement().executeQuery("select head_name from household_tbl where household_id='"+household_id+"' ");
		while(rs.next()){
			head_name=rs.getString("head_name");
		}
	}catch (Exception e) {
		// TODO: handle exception
	}
	return head_name;
	}
	
	//searching for household_id only view the barangay in household_tbl
	public String barangay(String household_id){
	String barangay=null;
	try{
		rs=con.createStatement().executeQuery("select barangay from household_tbl where household_id='"+household_id+"' ");
		while(rs.next()){
			barangay=rs.getString("barangay");
		}
	}catch (Exception e) {
		// TODO: handle exception
	}
	return barangay;
	}
	
	//searching for household_id only view the municipality in household_tbl
	public String municipal(String household_id){
	String municipal=null;
	try{
		rs=con.createStatement().executeQuery("select municipality from household_tbl where household_id='"+household_id+"' ");
		while(rs.next()){
			municipal=rs.getString("municipality");
		}
	}catch (Exception e) {
		// TODO: handle exception
	}
	return municipal;
	}
	
	//updating time and receive in received_tbl
	public void updaterecieve(String household_id,String month,String day,String time,int receive,String comment){
		try{
				con.createStatement().execute("update received_tbl set date_receive='"+day+"' ,time='"+time+"',receive='"+receive+"',comment='"+comment+"' where household_id='"+household_id+"' and month_and_year='"+month+"' ");
		}catch (Exception e) {
		// TODO: handle exception
		}
	}
	
	//viewing the data if the person receive the cash
	public ArrayList<BeansAdd>list_house(String household_id,String month){
	ArrayList<BeansAdd>list=new ArrayList<BeansAdd>();
	BeansAdd beans=null;
	try{
		rs =con.createStatement().executeQuery("select sub from received_tbl where household_id = '"+household_id+"' and month_and_year='"+month+"' ");
		while(rs.next()){
			if(rs.getInt(1) == 0){
				rs=con.createStatement().executeQuery("select household_id,month_and_year,date_receive,time,amount,receive,comment,sub from received_tbl where household_id='"+household_id+"' and month_and_year='"+month+"' order by receive ");
				while(rs.next()){
					beans=new BeansAdd(rs.getString("household_id"),
							rs.getString("month_and_year"),
							rs.getString("date_receive"),
							rs.getString("time"),
							rs.getFloat("amount"),
							rs.getInt("receive"),
							rs.getString("comment"),
							rs.getInt("sub"),
							"");
					list.add(beans);
				}
			}
			else{
				rs=con.createStatement().executeQuery("select household_id,month_and_year,date_receive,time,amount,receive,comment,sub,fname,lname from received_tbl,user_tbl where household_id='"+household_id+"' and sub = id  and month_and_year='"+month+"'  order by receive ");
				while(rs.next()){
					String munLink_name = rs.getString("fname")+" "+rs.getString("lname");
					beans=new BeansAdd(rs.getString("household_id"),
							rs.getString("month_and_year"),
							rs.getString("date_receive"),
							rs.getString("time"),
							rs.getFloat("amount"),
							rs.getInt("receive"),
							rs.getString("comment"),
							rs.getInt("sub"),
							munLink_name);
					list.add(beans);
				}
			}
		}
		
		
	}catch (Exception e) {
		e.printStackTrace();
		// TODO: handle exception
	}
	return list;
	}
	
	//view only household_id,month,year,reason in reason_tbl
	/*public ArrayList<Beanslistson>listreason(){
	ArrayList<Beanslistson>list=new ArrayList<Beanslistson>();
	Beanslistson beans=null;
	try{
		rs=con.createStatement().executeQuery("select household_id,month,year,reason from reason_tbl");
		while(rs.next()){
			beans=new Beanslistson(rs.getString("household_id"),
					rs.getInt("month"),
					rs.getInt("year"),
					rs.getString("reason"));
			list.add(beans);
		}
	}catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
	return list;
	}*/
	
	//searching for transaction record
	public ArrayList<Beanstransaction_record>list_record(String municipal,String barangay,String month){
		ArrayList<Beanstransaction_record>list=new ArrayList<Beanstransaction_record>();
		Beanstransaction_record beans=null;
		try{
			rs=con.createStatement().executeQuery("select h.household_id,head_name,amount,receive,sub from household_tbl as h,received_tbl as r " +
					"where h.household_id=r.household_id and municipality = '"+municipal+"' and barangay='"+barangay+"' and month_and_year='"+month+"' order by head_name ");
			while(rs.next()){
				beans=new Beanstransaction_record(rs.getString("household_id"),
						rs.getString("head_name"),
						rs.getFloat("amount"),
						rs.getInt("receive"),
						rs.getInt("sub"));
				list.add(beans);
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}
	
	// select month_year from receive_tbl
	public ArrayList<Month_Year>m_y_list(){
		ArrayList<Month_Year>list=new ArrayList<Month_Year>();
		Month_Year beans=null;
		try{
			rs=con.createStatement().executeQuery("select distinct month_and_year from received_tbl");
			while(rs.next()){
				beans=new Month_Year(rs.getString("month_and_year"));
				list.add(beans);
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}
	//household_id autocomplete
	public ArrayList<String>list_id(String household_id){
		ArrayList<String>list=new ArrayList<String>();
		try{
			rs=con.createStatement().executeQuery("select household_id from household_tbl where household_id like '%"+household_id+"%' ");
			while(rs.next()){
				list.add(rs.getString("household_id"));
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}
	//head_name autocomplete
	public ArrayList<String>list_head_name(String head_name){
		ArrayList<String>list=new ArrayList<String>();
		try{
			rs=con.createStatement().executeQuery("select head_name from household_tbl where head_name like '%"+head_name+"%' ");
			while(rs.next()){
				list.add(rs.getString("head_name"));
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}
	//spouse autocomplete
	public ArrayList<String>list_spouse(String wife){
		ArrayList<String>list=new ArrayList<String>();
		try{
			rs=con.createStatement().executeQuery("select spouse_name from wife_tbl where spouse_name like '%"+wife+"%' ");
			while(rs.next()){
				list.add(rs.getString("spouse_name"));
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}
	//student autocomplete
	public ArrayList<String>list_student(String name){
		ArrayList<String>list=new ArrayList<String>();
		try{
			rs=con.createStatement().executeQuery("select child_name from children_tbl as c,household_tbl as h " +
					"where h.household_id=c.household_id and (c.attending_school=1 or h.attending_school=1) and child_name like '%"+name+"%' union "+
					"(select spouse_name from wife_tbl as w,household_tbl as h " +
					"where h.household_id=w.household_id and (w.attending_school=1 or h.attending_school=1) and spouse_name like '%"+name+"%')  union " +
					"(select head_name from household_tbl as h,wife_tbl as w " +
					"where h.household_id=w.household_id and (h.attending_school=1 or w.attending_school=1) and head_name like '%"+name+"%')  union " +
					"(select grandchild_name from grandchild_tbl as g,household_tbl as h " +
					"where h.household_id=g.household_id and (h.attending_school=1 or g.attending_school=1) and grandchild_name like '%"+name+"%')");
			while(rs.next()){
				list.add(rs.getString("child_name"));
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}
	//municipal autocomplete
	public ArrayList<String>list_municipal(String name,String municipal,String brgy){
		ArrayList<String>list=new ArrayList<String>();
		try{
			rs=con.createStatement().executeQuery("select head_name from household_tbl where head_name like '%"+name+"%' and municipality='"+municipal+"' and barangay='"+brgy+"' ");
			while(rs.next()){
				list.add(rs.getString("head_name"));
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}
	//municipal autocomplete
	public ArrayList<String>list_municipal1(String name,String municipal){
		ArrayList<String>list=new ArrayList<String>();
		try{
			rs=con.createStatement().executeQuery("select head_name from household_tbl where head_name like '%"+name+"%' and municipality='"+municipal+"'");
			while(rs.next()){
				list.add(rs.getString("head_name"));
			}
		}catch (Exception e) {
			// TODO: handle exception
		}	
		return list;
	}
	//barangay autocomplete
	public ArrayList<String>list_brgy(String name){
		ArrayList<String>list=new ArrayList<String>();
		try{
			rs=con.createStatement().executeQuery("select distinct barangay from household_tbl where barangay like '%"+name+"%' ");
			while(rs.next()){
				list.add(rs.getString("barangay"));
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}
	//birth day
	public ArrayList<Beanstransaction>birth_list(String date){
		ArrayList<Beanstransaction>list=new ArrayList<Beanstransaction>();
		Beanstransaction beans=null;
		try{
			rs=con.createStatement().executeQuery("select household_id, household_member_id,head_name,barangay,municipality from household_tbl " +
					"where birthday='"+date+"'order by head_name ");
			while(rs.next()){
				beans = new Beanstransaction(rs.getString("household_id"), 
						rs.getInt("household_member_id"), 
						rs.getString("head_name"),
						rs.getString("barangay"),
						rs.getString("municipality"));
				list.add(beans);
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}
	//philhealth autocomplete
	public ArrayList<String>phil_list(String phil){
		ArrayList<String>list=new ArrayList<String>();
		try{
			rs=con.createStatement().executeQuery("select philhealth_id from household_tbl " +
					"where philhealth_id like '%"+phil+"%' ");
			while(rs.next()){
				list.add(rs.getString("philhealth_id"));
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}
	//sear philhealth id
	public ArrayList<Beanstransaction>philhealth_list(String phil){
		ArrayList<Beanstransaction>list=new ArrayList<Beanstransaction>();
		Beanstransaction beans=null;
		try{
			rs=con.createStatement().executeQuery("select household_id, household_member_id,head_name,barangay,municipality from household_tbl " +
					"where philhealth_id='"+phil+"' order by head_name ");
			while(rs.next()){
				beans = new Beanstransaction(rs.getString("household_id"), 
						rs.getInt("household_member_id"), 
						rs.getString("head_name"),
						rs.getString("barangay"),
						rs.getString("municipality"));
				list.add(beans);
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}
	
	public ArrayList<Beanstransaction>getlist(String search,String col){
		ArrayList<Beanstransaction>list=new ArrayList<Beanstransaction>();
		Beanstransaction beans=null;
		try{
			rs=con.createStatement().executeQuery("select household_id,household_member_id,head_name,barangay,municipality from household_tbl "
												+"where "+col+" like '%"+search+"%' and household_id not in(select household_id from fingerprint_tbl) "
												+"order by head_name");
			while(rs.next()){
				beans = new Beanstransaction(rs.getString("household_id"),
						rs.getInt("household_member_id"), 
						rs.getString("head_name"),
						rs.getString("barangay"),
						rs.getString("municipality"));
				list.add(beans);
			}
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<Beanstransaction>getmunicipality_list(String municipal,String brgy,String name){
		ArrayList<Beanstransaction>list=new ArrayList<Beanstransaction>();
		Beanstransaction beans=null;
		try{
			rs=con.createStatement().executeQuery("select household_id,household_member_id,head_name,barangay,municipality from household_tbl " +
					"where municipality like '%"+municipal+"%' and barangay='"+brgy+"' and head_name like '%"+name+"%'and household_id not in(select household_id from fingerprint_tbl) order by head_name");
			while(rs.next()){
				beans=new Beanstransaction(rs.getString("household_id"),
						rs.getInt("household_member_id"),
						rs.getString("head_name"),
						rs.getString("barangay"),
						rs.getString("municipality"));
				list.add(beans);
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}
	
	public ArrayList<Beanstransaction>getmunicipal_list1(String municipal,String name){
		ArrayList<Beanstransaction>list=new ArrayList<Beanstransaction>();
		Beanstransaction beans=null;
		try{
			rs=con.createStatement().executeQuery("select household_id,household_member_id,head_name,barangay,municipality from household_tbl " +
					"where municipality like '%"+municipal+"%' and head_name like '%"+name+"%' and household_id not in(select household_id from fingerprint_tbl) order by head_name");
			while(rs.next()){
				beans=new Beanstransaction(rs.getString("household_id"),
						rs.getInt("household_member_id"),
						rs.getString("head_name"),
						rs.getString("barangay"),
						rs.getString("municipality"));
				list.add(beans);
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<Beanstransaction>gethousehold_list(){
		ArrayList<Beanstransaction>list=new ArrayList<Beanstransaction>();
		Beanstransaction beans=null;
		try{
			rs=con.createStatement().executeQuery("select household_id,household_member_id,head_name,barangay,municipality from household_tbl "
												+"where household_id not in(select household_id from fingerprint_tbl) "
												+"order by head_name limit 20");
			while(rs.next()){
				beans = new Beanstransaction(rs.getString("household_id"),
						rs.getInt("household_member_id"), 
						rs.getString("head_name"),
						rs.getString("barangay"),
						rs.getString("municipality"));
				list.add(beans);
			}
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}
	public ArrayList<Beanstransaction> getwlist(String search, String col) {
		ArrayList<Beanstransaction>list=new ArrayList<Beanstransaction>();
		Beanstransaction beans=null;
		try{
			rs=con.createStatement().executeQuery("select h.household_id,h.household_member_id,h.head_name,h.barangay,h.municipality from household_tbl as h,wife_tbl as w  "
												+"where w.spouse_name like '%"+search+"%' and w.household_id = h.household_id and h.household_id not in(select household_id from fingerprint_tbl) "
												+"order by head_name");
			while(rs.next()){
				beans = new Beanstransaction(rs.getString("household_id"),
						rs.getInt("household_member_id"), 
						rs.getString("head_name"),
						rs.getString("barangay"),
						rs.getString("municipality"));
				list.add(beans);
			}
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<Beanstransaction>getlist2(String search,String col){
		ArrayList<Beanstransaction>list=new ArrayList<Beanstransaction>();
		Beanstransaction beans=null;
		try{
			rs=con.createStatement().executeQuery("select household_id,household_member_id,head_name,barangay,municipality from household_tbl "
												+"where "+col+" = "+search+" and household_id not in(select household_id from fingerprint_tbl) "
												+"order by head_name");
			while(rs.next()){
				beans = new Beanstransaction(rs.getString("household_id"),
						rs.getInt("household_member_id"), 
						rs.getString("head_name"),
						rs.getString("barangay"),
						rs.getString("municipality"));
				list.add(beans);
			}
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}
	public ArrayList<reportBean>list_brgy2(String name){
		ArrayList<reportBean>list=new ArrayList<reportBean>();
		reportBean bean = null;
		try{
			rs=con.createStatement().executeQuery("select distinct brgy_id,brgy_name from household_tbl,brgy_tbl where brgy_id = barangay and brgy_name like '%"+name+"%' ");
			while(rs.next()){
				bean = new reportBean(rs.getInt(1),rs.getString(2));
				System.out.println(rs.getInt(1)+rs.getString(2));
				list.add(bean);
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}
	
	
	//new methods..
	
	public ArrayList<Beanstransaction>gettemplist(String search,String col){
		ArrayList<Beanstransaction>list=new ArrayList<Beanstransaction>();
		Beanstransaction beans=null;
		try{
			rs=con.createStatement().executeQuery("select household_id,household_member_id,head_name,barangay,municipality from household_tbl "
												+"where "+col+" like '%"+search+"%' "
												+"order by head_name");
			while(rs.next()){
				beans = new Beanstransaction(rs.getString("household_id"),
						rs.getInt("household_member_id"), 
						rs.getString("head_name"),
						rs.getString("barangay"),
						rs.getString("municipality"));
				list.add(beans);
			}
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<String> getflist() {
		
		ArrayList<String> flist = new ArrayList<String>();
		String sql = "select household_id from fingerprint_tbl";
		
		try{
			
			rs = con.createStatement().executeQuery(sql);
			while(rs.next()){
				flist.add(rs.getString(1));
			}
			
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		
		return flist;
	}
	
	public ArrayList<Beanstransaction> gettempwlist(String search, String col) {
		ArrayList<Beanstransaction>list=new ArrayList<Beanstransaction>();
		Beanstransaction beans=null;
		try{
			rs=con.createStatement().executeQuery("select h.household_id,h.household_member_id,h.head_name,h.barangay,h.municipality from household_tbl as h,wife_tbl as w  "
												+"where w.spouse_name like '%"+search+"%' and w.household_id = h.household_id  "
												+"order by head_name");
			while(rs.next()){
				beans = new Beanstransaction(rs.getString("household_id"),
						rs.getInt("household_member_id"), 
						rs.getString("head_name"),
						rs.getString("barangay"),
						rs.getString("municipality"));
				list.add(beans);
			}
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<Beanstransaction>gettempmunicipality_list(String municipal,String brgy,String name){
		ArrayList<Beanstransaction>list=new ArrayList<Beanstransaction>();
		Beanstransaction beans=null;
		try{
			rs=con.createStatement().executeQuery("select household_id,household_member_id,head_name,barangay,municipality from household_tbl " +
					"where municipality like '%"+municipal+"%' and barangay='"+brgy+"' and head_name like '%"+name+"%' order by head_name");
			while(rs.next()){
				beans=new Beanstransaction(rs.getString("household_id"),
						rs.getInt("household_member_id"),
						rs.getString("head_name"),
						rs.getString("barangay"),
						rs.getString("municipality"));
				list.add(beans);
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}
	
	public ArrayList<Beanstransaction>gettemplist2(String search,String col){
		ArrayList<Beanstransaction>list=new ArrayList<Beanstransaction>();
		Beanstransaction beans=null;
		try{
			rs=con.createStatement().executeQuery("select household_id,household_member_id,head_name,barangay,municipality from household_tbl "
												+"where "+col+" = "+search+"  "
												+"order by head_name");
			while(rs.next()){
				beans = new Beanstransaction(rs.getString("household_id"),
						rs.getInt("household_member_id"), 
						rs.getString("head_name"),
						rs.getString("barangay"),
						rs.getString("municipality"));
				list.add(beans);
			}
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}
	public ArrayList<Brgy> getAllbrgy(String municipal) {
		ArrayList<Brgy> list = new ArrayList<Brgy> ();
		String sql = "select brgy_id,brgy_name from brgy_tbl where mun_id = '"+municipal+"'";
		Brgy bean = null;
		try{
			
			rs = con.createStatement().executeQuery(sql);
			while(rs.next()){
				bean = new Brgy(rs.getInt(1),rs.getString(2));
				list.add(bean);
			}
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		
		return list;
	}
public float getLastAmount(String id) {
		
		float am = 0;
		
		try{
			
			/*rs = con.createStatement().executeQuery("select `amount` from received_tbl where parsing_id = (select max(parsing_id) from received_tbl where household_id = '"+id+"') and household_id = '"+id+"'");*/
			rs = con.createStatement().executeQuery("select sum(`amount`) from received_tbl where household_id = '"+id+"' and receive = 0");
			while(rs.next()){
				am = rs.getFloat(1);
			}
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		
		return am;
		
	}
public ArrayList<transactionBean> getSubANDDateOfTransaction(String id) {

	ArrayList<transactionBean> l1 = new ArrayList<transactionBean>();
	transactionBean bean = null;
	try{
		
		rs =con.createStatement().executeQuery("select sub,month_and_year from received_tbl where household_id = '"+id+"'");
		while(rs.next()){
			bean = new transactionBean(rs.getInt(1),rs.getString(2));
			l1.add(bean);
		}
		
	}
	catch(Exception ex){
		System.out.println(ex);
	}
	
	return l1;
}
public BeansAdd getDataByGrantee(String dateoftransaction, String id) {
	BeansAdd bean = null;
	
	try{
		
		rs=con.createStatement().executeQuery("select household_id,month_and_year,date_receive,time,amount,receive,comment,sub from received_tbl where household_id='"+id+"' and month_and_year='"+dateoftransaction+"' order by receive ");
		System.out.println("select household_id,month_and_year,date_receive,time,amount,receive,comment,sub from received_tbl where household_id='"+id+"' and month_and_year='"+dateoftransaction+"' order by receive ");
		while(rs.next()){
			bean=new BeansAdd(rs.getString("household_id"),
					rs.getString("month_and_year"),
					rs.getString("date_receive"),
					rs.getString("time"),
					rs.getFloat("amount"),
					rs.getInt("receive"),
					rs.getString("comment"),
					rs.getInt("sub"),
					"");
		}
			
		
	}
	catch(Exception ex){
		System.out.println(ex);
	}
	
	return bean;
}
public BeansAdd getDataByGrantee2(String dateoftransaction, String id,int sub) {
	BeansAdd bean = null;
	
	try{
		
		rs=con.createStatement().executeQuery("select household_id,month_and_year,date_receive,time,amount,receive,comment,sub,fname,lname from received_tbl,user_tbl where household_id='"+id+"' and sub = id  and month_and_year='"+dateoftransaction+"' and id = "+sub+" order by receive ");
		System.out.println("select household_id,month_and_year,date_receive,time,amount,receive,comment,sub,fname,lname from received_tbl,user_tbl where household_id='"+id+"' and sub = id  and month_and_year='"+dateoftransaction+"' and id = "+sub+" order by receive ");
		while(rs.next()){
			String munLink_name = rs.getString("fname")+" "+rs.getString("lname");
			bean=new BeansAdd(rs.getString("household_id"),
					rs.getString("month_and_year"),
					rs.getString("date_receive"),
					rs.getString("time"),
					rs.getFloat("amount"),
					rs.getInt("receive"),
					rs.getString("comment"),
					rs.getInt("sub"),
					munLink_name);
		}
			
	}
	catch(Exception ex){
		System.out.println(ex);
	}
	
	return bean;
}
}
