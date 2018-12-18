package info;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.Serializable;
import java.net.UnknownHostException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import tool.Client;
import view.Shopview;

public class Employee implements Serializable{
	public static String ID;
	private String pw;
	public static String name;
	public Employee(String ID,String pw,String name) {
		// TODO Auto-generated constructor stub
		this.ID = ID;
		this.pw = pw;
		this.name = name;
	}
	
	public Employee(){}
	
	public float getTicket(String pid,String hid,String fname,int hot,String starttime,int[][] waitsize,float free,String vipid,boolean isvip,float money) {
		// TODO Auto-generated method stub
		//Ա��Ϊ�˿͹�Ʊ����Ʊ��hot����+1 �ڷ���˸��ģ�
		int index = 0;
		for(int i = 0;i<5;i++){
			if(waitsize[i][0]==1){
				index = index + 1;
				//���ÿͻ���-Fname,Hname,statttime(�ͻ���ÿ����һ�����󣬾ͷ��͸������һ��hot+1������)
				try {
					System.out.println(i);
					hot = new Client().getTicket(pid,hid,fname, hot,waitsize[i][1] , waitsize[i][2]);
					if(hot == -1) {
						System.out.println("���ʧ��");
						JOptionPane.showMessageDialog(null, "��"+index+"���ύʧ��   ��λ��Ϊ����-"+(waitsize[i][1]+1)+"��-"+(waitsize[i][2]+1));
						new Employee().saveWorknote("��"+index+"���ύʧ��   ��λ��Ϊ����-"+(waitsize[i][1]+1)+"��-"+(waitsize[i][2]+1));
					}else {
						JOptionPane.showMessageDialog(null, "��"+index+"���ύ�ɹ�   ��λ��Ϊ����-"+(waitsize[i][1]+1)+"��-"+(waitsize[i][2]+1));
						new Employee().saveWorknote("��"+index+"���ύ�ɹ�   ��λ��Ϊ����-"+(waitsize[i][1]+1)+"��-"+(waitsize[i][2]+1));
						money = new Client().consume(free,vipid,money);
						new Employee().saveWorknote("�۷ѳɹ�-vip��"+vipid+"-�۷ѣ�"+free);
						if(money < free && waitsize[i+1][0]==1) {
							JOptionPane.showMessageDialog(null, "����");
							break;
						}
					}
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
		if(index == 0){
			JOptionPane.showMessageDialog(null, "����û��ѡ����λ");
		}else {
			if(isvip) {
			JOptionPane.showMessageDialog(null, "��Ʊ�ɹ����������Ϊ:"+money+"��ף���������");
			}else {
				JOptionPane.showMessageDialog(null, "��Ʊ�ɹ���ף���������");
			}
		}
		System.out.println(index);
		return money;
	}
	
	public float checkVIP(JButton jbok,JButton buy,JTextField text) throws ClassNotFoundException, UnknownHostException, IOException{
		//����ID
		
		float money = new Client().getVip(text.getText());
		if(money== -1){
			new Employee().saveWorknote("�Ҳ�����Ա"+text.getText());
			JOptionPane.showMessageDialog(null, "�Ҳ�����Ա"+text.getText());
		}else{
			new Employee().saveWorknote("�ҵ���Ա"+text.getText());
			JOptionPane.showMessageDialog(null, "�ҵ���Ա"+text.getText());
			buy.setEnabled(true);
			text.setText("��ǰ�û����Ϊ:"+money);
			text.setEnabled(false);
			jbok.setText("���������Ա����");
		}
			return money;//���ؿ������
	}
	
	public void getCommodity(String Cid,float price,int num){
		//Ա��Ϊ�˿͹�����ʳ����Ʒ
		try {
			new Client().getCommodity(Cid,price,num);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		}
	
	public int ELogin(String id,String pw){
		//Ա����¼
		try {
			
			if(id.equals("") || pw.equals("")){
				JOptionPane.showMessageDialog(null, "��δ�����˺Ż�����");	
				}
			else{
				
			Client c = new Client();
		String name = c.Elogin(id, pw);
		if(name.equals("")){
			JOptionPane.showMessageDialog(null, "��¼ʧ��");
		}
		else{
			this.name = name;
			this.ID = id;
		JOptionPane.showMessageDialog(null, "��¼�ɹ�");
		new Employee().saveWorknote("Ա����¼");
		return 1;
		}
			}
			
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	public float addMoney(String id,float addmoney) throws UnknownHostException, IOException{
		//�˿ͳ�ֵ
		float money = new Client().addMoney(id, addmoney);
		if(money == -1){
			JOptionPane.showMessageDialog(null, "�˺Ų�����");
		}else{
			JOptionPane.showMessageDialog(null, "��ֵ�ɹ�");
			new Employee().saveWorknote("Ϊ�û���"+id+"��ֵ"+addmoney+"Ԫ");
		}
		return money;
	}
	public void addCard(String ID,String name,String remarks){
		//�����Ա��
		try {
			String ok = new Client().Transaction(ID, name, remarks);
			if(ok.equals("ok")){
				JOptionPane.showMessageDialog(null, "����ɹ�");
				new Employee().saveWorknote("Ϊ�û���"+name+" ����VIP");
			}else{
				JOptionPane.showMessageDialog(null, "����ʧ��");
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public int[][] chooseSeat(int x,int y,String pid){
		//�˿͹�Ʊǰ��ѡ����λ
		//��ȡӰ����λ��С
		//��ȡ��ǰ�ѱ��������λ��1Ϊ�ѱ�����0Ϊ����
		int[][] seat = new int[x][y];
		
		if(!pid.equals("")) {	
			try {
				int[][] selectedlist = new Client().getSelectedSeat(pid);
				for(int i = 0;i<selectedlist.length;i++) {
					seat[selectedlist[i][0]][selectedlist[i][1]] = 1;
				}
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return seat;
	}
	public void saveWorknote(String operation) {
		//��¼Ա�������в�������¼��¼����Ʊ�����������һ����¼�洢�����ݿ�
		
		try {
			System.out.println("id:"+Employee.ID);
			String b = new Client().MakeOperationNote(Employee.ID, Employee.name,operation);
			System.out.println("��ӽ�������¼");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    
	}
	public Object[][] showVipUser(){
		Object[][] obj = null;
		try {
			obj = new Client().showVip();
			new Employee().saveWorknote("�鿴����VIP�û�");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj;	
	}
	
	public List showFilm(){
		try {
			List list = new Client().showFilm();
			new Employee().saveWorknote("�鿴���е�Ӱ");
			return list;
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}return null;
	}
	
	public List managerHall(){
		try {
			List list = new Client().managerHall();
			new Employee().saveWorknote("�鿴Ӱ������");
			return list;
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}return null;
	}
	
	public String getCommcover(String cid) throws UnknownHostException, IOException {
		return new Client().getCommcover(cid);
	}
	
	public String getFilmcover(String fname) throws UnknownHostException, IOException {
		return new Client().getFilmcover(fname);
	}
	
	public static void main(String[] args) {
		
	}
	
}
