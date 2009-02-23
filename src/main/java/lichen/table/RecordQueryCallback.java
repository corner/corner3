package lichen.table;

import java.util.List;

/**
 * 用于RecordQueryModelImpl提供数据的回调接口
 * 
 * @author dong
 * @version $Revision$
 * @since 0.0.2
 */
public interface RecordQueryCallback {

	/**
	 * 取得由currentPage指定的页数的数据
	 * 
	 * @param currentPage
	 *            分页数据的第几页
	 * @param rowsPerPage
	 *            每页的行数
	 * @return
	 * @since 0.0.2
	 */
	public List<?> getData(int currentPage, int rowsPerPage);

	/**
	 * 取得记录的总数
	 * 
	 * @return
	 * @since 0.0.2
	 */
	public int getCount();

}
