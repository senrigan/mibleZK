package com.gdc.nms.web.mibquery;


import java.io.IOException;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.Map;

import org.snmp4j.CommunityTarget;
import org.snmp4j.MessageDispatcher;
import org.snmp4j.PDU;
import org.snmp4j.PDUv1;
import org.snmp4j.Snmp;
import org.snmp4j.mp.MPv1;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.smi.Variable;
import org.snmp4j.transport.DefaultUdpTransportMapping;

import com.gdc.nms.model.Device;


public class Snmp1Connector extends Snmp2Connector implements SnmpConnector {

    @Override
    public void open(Device device) throws SnmpConnectorException {
        if (snmp == null) {
            try {
                setDevice(device);
                DefaultUdpTransportMapping transport = new DefaultUdpTransportMapping();
                // transport socket timeout set to 0 as suggested in:
                // http://lists.agentpp.org/pipermail/snmp4j/2006-February/001030.html
                // to make Snmp.close()/DefaultUdpTransportMapping.close()
                // return
                // immediatly
                transport.setSocketTimeout(0);
                snmp = new Snmp();
                MessageDispatcher disp = snmp.getMessageDispatcher();
                disp.addCommandResponder(snmp);
                disp.addMessageProcessingModel(new MPv1());
                snmp.addTransportMapping(transport);
                snmp.listen();

                String publicCommunity = device.getSnmpPublicCommunity();
                if (publicCommunity == null) {
                    publicCommunity = "";
                }
                target = new CommunityTarget(new UdpAddress(InetAddress.getByName(device.getIp()), 161),
                    new OctetString(publicCommunity));
                target.setTimeout(device.getSnmpTimeout());
                target.setRetries(device.getSnmpRetries());
            } catch (IOException e) {
                throw new SnmpConnectorException(e);
            }
        }
    }

    @Override
    protected PDU newPdu() {
        return new PDUv1();
    }

    public static void main(String[] args) throws SnmpConnectorException {
        Device dev = new Device("10.151.0.74");
        Snmp1Connector snmp1Connector = new Snmp1Connector();

        try {
            snmp1Connector.open(dev);

            Map<OID, Variable> variable = snmp1Connector.walk(Arrays.asList(
                new OID(StandardOid.IF_OPER_STATUS.getValue()), new OID(StandardOid.IF_ADMIN_STATUS.getValue()),
                new OID(StandardOid.IF_LAST_CHANGE.getValue())));
            System.out.println(variable);
        } finally {
            snmp1Connector.close();
        }
    }
}

