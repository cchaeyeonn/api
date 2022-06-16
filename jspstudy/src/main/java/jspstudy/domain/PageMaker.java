//PageMaker.java

package jspstudy.domain;

public class PageMaker {	//전체개수를 담고 각 개수마다 리스트에 나타내주는 페이지번호를 알아내기 위한 클래스
	
	private int totalCount;	//전체 데이터 개수
	private int startPage;	//하단 네비게이션 시작 첫번째 번호
	private int endPage;	//네비게이션 마지막 번호
	private boolean prev;	//이전버튼
	private boolean next;	//다음버튼
	private int displayPageNum = 10;
	private SearchCriteria scri;	//인스턴스 변수
	
	
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
	
	
	//시작페이지 끝페이지 이전 다음 버튼 값을 생성하는 메소드
	public void calcDate() {
		
		endPage = (int)(Math.ceil(scri.getPage()/(double)displayPageNum) * displayPageNum);	// 1/10이 0.1인데 올려서 1 | ceil메소드 소수점올림처리
		startPage = (endPage-displayPageNum)+1;	//10개+1
		
		int tempEndPage = (int)(Math.ceil(totalCount/(double)scri.getPerPageNum()));	//실제페이지 개수
		
		if(endPage > tempEndPage) {
			endPage = tempEndPage;
		}
		
		prev = startPage == 1 ? false:true;
		next = endPage*scri.getPerPageNum()>=totalCount ? false:true;
		
	}
	

}
