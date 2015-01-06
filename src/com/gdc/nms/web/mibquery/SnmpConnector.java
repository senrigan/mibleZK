/**
 * 
 */


import java.util.List;
import java.util.Map;

import org.snmp4j.smi.OID;
import org.snmp4j.smi.Variable;

import com.gdc.nms.common.Pair;
import com.gdc.nms.model.Device;

/**
 * @author lramos
 * 
 */
public interface SnmpConnector {

    public void open(Device device) throws SnmpConnectorException;

    public void close() throws SnmpConnectorException;

    /**
     * 
     * @param oid
     * @return
     * @throws SnmpConnectorException
     *             if an error occurs. Common errors are: unable to send the
     *             SNMP request, wrong authentication or timeout expiration.
     */
    public Map<OID, Variable> walk(OID oid) throws SnmpConnectorException;

    /**
     * 
     * @param oids
     * @return
     * @throws SnmpConnectorException
     *             if an error occurs. Common errors are: unable to send the
     *             SNMP request, wrong authentication or timeout expiration.
     */
    public Map<OID, Variable> walk(List<OID> oids) throws SnmpConnectorException;

    /**
     * 
     * @param oids
     * @return
     * @throws SnmpConnectorException
     *             if an error occurs. Common errors are: unable to send the
     *             SNMP request, wrong authentication or timeout expiration.
     */
    public Map<OID, Variable> get(List<OID> oids) throws SnmpConnectorException;

    public Map<OID, Variable> get(List<OID> oids, int maxSize) throws SnmpConnectorException;

    /**
     * 
     * @param oid
     * @return
     * @throws SnmpConnectorException
     *             if an error occurs. Common errors are: unable to send the
     *             SNMP request, wrong authentication or timeout expiration.
     */
    public Variable get(OID oid) throws SnmpConnectorException;

    public Variable set(OID oid, Variable value) throws SnmpConnectorException;

    public List<Pair<OID, Variable>> set(List<Pair<OID, ? extends Variable>> parameters) throws SnmpConnectorException;

    public void setMaxGetSize(int maxGetSize);

}
