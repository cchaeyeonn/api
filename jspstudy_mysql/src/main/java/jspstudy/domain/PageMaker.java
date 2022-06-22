//PageMaker.java

package jspstudy.domain;

public class PageMaker {	//��ü������ ��� �� �������� ����Ʈ�� ��Ÿ���ִ� ��������ȣ�� �˾Ƴ��� ���� Ŭ����
	
	private int totalCount;	//��ü ������ ����
	private int startPage;	//�ϴ� �׺���̼� ���� ù��° ��ȣ
	private int endPage;	//�׺���̼� ������ ��ȣ
	private boolean prev;	//������ư
	private boolean next;	//������ư
	private int displayPageNum = 10;
	private SearchCriteria scri;	//�ν��Ͻ� ����
	
	
	public SearchCriteria getScri() {
		return scri;
	}
	public void setScri(SearchCriteria scri) {
		this.scri = scri;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		calcDate();
	}
	public int getStartPage() {
		return startPage;
	}
	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}
	public int getEndPage() {
		return endPage;
	}
	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}
	public boolean isPrev() {
		return prev;
	}
	public void setPrev(boolean prev) {
		this.prev = prev;
	}
	public boolean isNext() {
		return next;
	}
	public void setNext(boolean next) {
		this.next = next;
	}
	public int getDisplayPageNum() {
		return displayPageNum;
	}
	public void setDisplayPageNum(int displayPageNum) {
		this.displayPageNum = displayPageNum;
	}
	
	
	//���������� �������� ���� ���� ��ư ���� �����ϴ� �޼ҵ�
	public void calcDate() {
		
		endPage = (int)(Math.ceil(scri.getPage()/(double)displayPageNum) * displayPageNum);	// 1/10�� 0.1�ε� �÷��� 1 | ceil�޼ҵ� �Ҽ����ø�ó��
		startPage = (endPage-displayPageNum)+1;	//10��+1
		
		int tempEndPage = (int)(Math.ceil(totalCount/(double)scri.getPerPageNum()));	//���������� ����
		
		if(endPage > tempEndPage) {
			endPage = tempEndPage;
		}
		
		prev = startPage == 1 ? false:true;
		next = endPage*scri.getPerPageNum()>=totalCount ? false:true;
		
	}
	

}
