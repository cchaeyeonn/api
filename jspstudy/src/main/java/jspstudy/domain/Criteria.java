//Criteria.java

package jspstudy.domain;

public class Criteria {//페이지에 대한 값담기
	private int page;			//페이지번호 담기
	private int perPageNum;		//화면에 리스트 출력 개수

	
	public Criteria() {	//기본값 지정
		this.page = 1;
		this.perPageNum = 15;
	}


	public int getPage() {
		return page;
	}


	public void setPage(int page) {
		
		if(page<=1) {
			this.page = 1;
			return;
		}
		this.page = page;
	}


	public int getPerPageNum() {
		return perPageNum;
	}


	public void setPerPageNum(int perPageNum) {
		this.perPageNum = perPageNum;
	}
	
	
}
