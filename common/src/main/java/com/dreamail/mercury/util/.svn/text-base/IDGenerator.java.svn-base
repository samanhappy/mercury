/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 
package com.dreamail.mercury.util;

import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.ibatis.session.SqlSession;

import com.dreamail.mercury.dal.SessionFactory;
import com.dreamail.mercury.pojo.Clickoo_ids;

*//**
 *
 * @author tiger
 *//*
public class IDGenerator {

    private static Map<String, IDGenerator> instances = new ConcurrentHashMap<String, IDGenerator>();
    private static SqlSession session = null;
    private static final String namespace = "com.dreamail.mercury.domain.IDsMapper";
    private static final String queryId = "selectIDs";
    private static final String insertId = "insertIDs";
    private static final String updateNonceId = "updateNonce";
    private Clickoo_ids result = null;
    private Long nonce;
    private Long upper;
    private String idType;
    
    *//**
     * 调用此方法，必须手工关闭session.
     * @param idType
     * @return
     * @throws Exception
     *//*
    public synchronized static Long nextID(String idType) throws Exception {
        IDGenerator idg = instances.get(idType);
        if (idg == null) {
        	synchronized(namespace){
        		 idg = instances.get(idType);
                 if(idg!=null) return idg.next();
                 idg = new IDGenerator(idType);
                 idg.reload();
                 instances.put(idType, idg);
        	}
        }
        return idg.next();
    }
    

    private IDGenerator(String idType) throws Exception {
        this.idType = idType;
    }

    private Long next() {
        if (nonce >= upper) {
            reload();
            return ++nonce;
        }
        return ++nonce;
    }

    private void reload() {
    	try {
			session = SessionFactory.getSession();
		} catch (SQLException e) {
			e.printStackTrace();
		}
        result = (Clickoo_ids) session.selectOne(namespace + "." + queryId, idType);
        if (result == null) {
            result = new Clickoo_ids();
            result.setName(idType);
            session.insert(namespace + "." + insertId, result);
        }
        nonce = result.getNonce();
        upper = nonce + result.getSteps();
        result.setNonce(upper);
        session.update(namespace + "." + updateNonceId, result);
        session.commit();
        //处于效率考虑，此处不关闭session，由调用的地方统一关闭
//        SessionFactory.closeSession();
    }
}
*/