package cc.cnfc.mapper.dict;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import cc.cnfc.dao.TestCustomerRoleDto;
import cc.cnfc.entity.ac.Customer;

public interface DictMapper {

	@Select("SELECT * FROM m_customer a left join m_customer_role b on a.mSysId=b.mSysId WHERE a.mSysId=#{customer.mSysId} and a.mName=#{customer.mName}")
	public TestCustomerRoleDto find(String dict_key);

	@Select("SELECT * FROM m_customer WHERE mSysId=#{mSysId}")
	public Customer findByMSysId(@Param("mSysId") String mSysId);

	@Select("SELECT * FROM m_customer")
	public List<Customer> findLs(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize);

}
