package com.gdc.nms.web.mibquery;

/**
 * 
 */


import static com.gdc.nms.common.Constants.CONNECTION_TEST_TIMEOUT;
import static com.gdc.nms.common.Constants.INTENSIVE_CONNECTION_TEST_DURATION;
import static com.gdc.nms.common.Constants.SNMP_RETRIES;
import static com.gdc.nms.common.Constants.SNMP_TIMEOUT;
import static javax.persistence.GenerationType.SEQUENCE;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.MapKey;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import com.gdc.nms.common.bean.ClassUtil;
import com.gdc.nms.model.listener.JpaEntityListener;
import com.gdc.nms.model.util.Interfaces;
import com.gdc.nms.model.visualAttributes.Link;
import com.gdc.nms.model.visualAttributes.Linkable;

/**
 * Represents a real device in a network. Devices can be switches, routers,
 * PBXs, etc...
 * 
 * @author Jonatan Rico (jrico) - jonatan.rico@gdc-cala.com.mx
 */
@Entity
@NamedQueries({
        @NamedQuery(name = Device.FIND_ALL, query = "SELECT d FROM Device d"),
        @NamedQuery(name = Device.FIND_STATS_COUNT, query = "SELECT COUNT(d) FROM Device d WHERE mod(d.flags,4) >= 3 AND  mod(d.flags/64,2) >= 1"),
        @NamedQuery(name = Device.FIND_BY_IP, query = "SELECT d FROM Device d WHERE d.ip = :ip"),
        @NamedQuery(name = Device.FIND_BY_ALIAS_PROJECT, query = "SELECT d FROM Device d WHERE d.alias = :alias AND d.projectId = :projectId"),
        @NamedQuery(name = Device.FIND_BY_ALIAS, query = "SELECT d FROM Device d WHERE d.alias = :alias"),
        @NamedQuery(name = Device.FIND_BY_PROJECT, query = "SELECT d FROM Device d WHERE d.projectId = :projectId") })
@SequenceGenerator(name = "Dev_Gen", sequenceName = "Dev_Seq")
@EntityListeners(JpaEntityListener.class)
public class Device extends NmsEntity implements Linkable, Comparable<Device> {

    /**
     * 
     */
    private static final long serialVersionUID = -832353474607336433L;

    public static final String FIND_ALL = "Device.findAll";

    public static final String FIND_STATS_COUNT = "Device.findStatsCount";

    public static final String FIND_BY_IP = "Device.findByIP";

    public static final String FIND_BY_ALIAS = "Device.findByAlias";

    public static final String FIND_BY_ALIAS_PROJECT = "Device.findByAliasProject";

    public static final String FIND_BY_PROJECT = "Device.findByProject";

    // bitwise flags constants

    private static final int REACHABLE = 0x00000001;

    private static final int MANAGED = 0x00000002;

    private static final int PINGABLE = 0x00000004;

    private static final int SNMPABLE = 0x00000008;

    private static final int WARNING = 0x00000010;

    private static final int SNMP_UNREACHABLE = 0x00000020;

    private static final int TRACKING_STATS = 0x00000040;

    private static final int TELNETABLE = 0x00000080;

    // TODO: free flag
    private static final int __FREE_FLAG__ = 0x00000100;

    private static final int INVENTORY = 0x00000200;

    private static final int REACHABLE_BY_REDUNDANT = 0x00000400;

    private static final int UPS_ON_BATTERY = 0x00000800;

    private static final int UPS_REMAINING_CHARGE = 0x00001000;

    private static final int UPS_BATTERY_STATUS = 0x00002000;

    private static final int DEVICE_CONFIG = 0x00004000;

    private static final int SYSLOG_SEVERITY_0 = 0x00008000;

    private static final int SYSLOG_SEVERITY_1 = 0x00010000;

    private static final int SYSLOG_SEVERITY_2 = 0x00020000;

    private static final int SYSLOG_SEVERITY_3 = 0x00040000;

    private static final int SYSLOG_SEVERITY_4 = 0x00080000;

    private static final int SYSLOG_SEVERITY_5 = 0x00100000;

    private static final int SYSLOG_SEVERITY_6 = 0x00200000;

    private static final int SYSLOG_SEVERITY_7 = 0x00400000;

    // Shared Project Config flags
    private static final int SHARED_PING_CONFIG = 0x00000001;

    private static final int SHARED_SNMP_CONFIG = 0x00000002;

    private static final int SHARED_CLI_CONFIG = 0x00000004;

    private static final int SHARED_TRAPS_CONFIG = 0x00000008;

    private static final int SYSLOG_0_ENABLED = 0x00100000;

    private static final int SYSLOG_1_ENABLED = 0x00200000;

    private static final int SYSLOG_2_ENABLED = 0x00400000;

    private static final int SYSLOG_3_ENABLED = 0x00800000;

    private static final int SYSLOG_4_ENABLED = 0x01000000;

    private static final int SYSLOG_5_ENABLED = 0x02000000;

    private static final int SYSLOG_6_ENABLED = 0x04000000;

    private static final int SYSLOG_7_ENABLED = 0x08000000;

    private static final int TRAPS_ENABLED = 0x10000000;

    /* Riverbed Steelhead */

    private static final int STEELHEAD_SYSTEM_HEALTH = 0x00000001;

    private static final int STEELHEAD_OPT_SERVICE_STATUS = 0x00000002;

    private static final int STEELHEAD_CPU_UTIL = 0x00000004;

    private static final int STEELHEAD_BYPASS_MODE = 0x00000008;

    private static final int STEELHEAD_ADMISSION_MEM_ERROR = 0x00000010;

    private static final int STEELHEAD_ADMISSION_CONN_ERROR = 0x00000020;

    private static final int STEELHEAD_HALT_ERROR = 0x00000040;

    private static final int STEELHEAD_SERVICE_ERROR = 0x00000080;

    private static final int STEELHEAD_LINK_ERROR = 0x00000100;

    private static final int STEELHEAD_ASYM_ROUTE_ERROR = 0x00000200;

    private static final int STEELHEAD_TEMPERATURE_WARNING = 0x00000400;

    private static final int STEELHEAD_TEMPERATURE_CRITICAL = 0x00000800;

    private static final int STEELHEAD_DISK_ERROR = 0x00001000;

    private static final int STEELHEAD_ADMISSION_CPU_ERROR = 0x00002000;

    private static final int STEELHEAD_ADMISSION_TCP_ERROR = 0x00004000;

    private static final int STEELHEAD_SYSTEM_DISK_FULL_ERROR = 0x00008000;

    private static final int STEELHEAD_CERTS_EXPIRING_ERROR = 0x00010000;

    private static final int STEELHEAD_LICENSE_ERROR = 0x00020000;

    private static final int STEELHEAD_HARDWARE_ERROR = 0x00040000;

    private static final int STEELHEAD_LAN_WAN_LOOP_ERROR = 0x00080000;

    private static final int STEELHEAD_OPTIMIZATION_SERVICE_STATUS_ERROR = 0x00100000;

    private static final int STEELHEAD_LICENSE_EXPIRING = 0x00200000;

    private static final int STEELHEAD_LICENSE_EXPIRED = 0x00400000;

    @Id
    @GeneratedValue(generator = "Dev_Gen", strategy = SEQUENCE)
    private long id;

    private Type type;

    private SnmpVersion snmpVersion;

    private CliProtocol protocolType;

    // sysUpTime - 1.3.6.1.2.1.1.3 - TimeTicks
    @Column(nullable = false)
    private long sysUpTime;

    private int flags;

    private int configurationFlags;

    private int steelheadFlags;

    private int testTimeout;

    private int intensiveTestDuration;

    private int snmpTimeout;

    private int snmpRetries;

    private String ip;

    private String hostname;

    private String alias;

    private String description;

    private String contact;

    private String location;

    private String snmpPublicCommunity;

    private String snmpUser;

    private String authenticationProtocol;

    private int securityLevel;

    private String sysObjectID;

    private String latencyMeasurementIp;

    private String statsTableId;

    private String telnetUser;

    private String telnetPassword;

    private String privilegeModePassword;

    private int maxGetSize;

    private long lastUpdateTime;

    @Column(name = "PROJECT_ID")
    private long projectId;

    // @OneToOne(cascade = { CascadeType.PERSIST, CascadeType.REFRESH,
    // CascadeType.REMOVE }, fetch = FetchType.EAGER)
    // @JoinFetch(JoinFetchType.OUTER)
    // private JGraphAttributes graphAttributes;

    @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE }, mappedBy = "device", fetch = FetchType.EAGER)
    private Set<Interface> interfaces;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE }, fetch = FetchType.EAGER)
    @JoinTable(name = "DEVICE_LINK", joinColumns = @JoinColumn(name = "DEVICE_ID"), inverseJoinColumns = @JoinColumn(name = "LINK_ID"))
    private Set<Link> links;

    @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE }, mappedBy = "device", fetch = FetchType.EAGER)
    private Set<IpSla> ipSlas;

    @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE }, mappedBy = "device", fetch = FetchType.EAGER)
    private List<DeviceResource> deviceResources;

    @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE }, fetch = FetchType.EAGER)
    @JoinTable(name = "DEVICE_PROPERTY", joinColumns = @JoinColumn(name = "DEVICE_ID"), inverseJoinColumns = @JoinColumn(name = "PROPERTY_ID"))
    @MapKey(name = "name")
    private Map<String, Property> properties;

    protected Device() {
    }

    public Device(String ip) {
        if (ip == null || ip.length() == 0) {
            throw new IllegalArgumentException();
        }
        this.ip = ip;
        flags = MANAGED | PINGABLE | SNMPABLE;
        testTimeout = CONNECTION_TEST_TIMEOUT;
        intensiveTestDuration = INTENSIVE_CONNECTION_TEST_DURATION;
        snmpTimeout = SNMP_TIMEOUT;
        snmpRetries = SNMP_RETRIES;
        statsTableId = "";
        protocolType = CliProtocol.TELNET;
        // graphAttributes = new JGraphAttributes(0, 0);
        interfaces = new HashSet<Interface>(5);
        links = new HashSet<Link>(3);
        ipSlas = new HashSet<IpSla>(3);
        deviceResources = new ArrayList<DeviceResource>(5);
        properties = new HashMap<String, Property>();
    }

    /**
     * @return the id
     */
    @Override
    public long getId() {
        return id;
    }

    /**
     * @return the projectId
     */
    public long getProjectId() {
        return projectId;
    }

    /**
     * @param projectId
     *            the projectId to set
     */
    public void setProjectId(long projectId) {
        long oldProject = getProjectId();
        this.projectId = projectId;
        firePropertyChange(Prop.projectId, oldProject, projectId);
    }

    /**
     * @return the sysObjectID
     */
    public String getSysObjectID() {
        return sysObjectID;
    }

    /**
     * @param sysObjectID
     *            the sysObjectID to set
     */
    public void setSysObjectID(String sysObjectID) {
        String oldSysObjectID = getSysObjectID();
        this.sysObjectID = sysObjectID;
        firePropertyChange(Prop.sysObjectID, oldSysObjectID, sysObjectID);
    }

    /**
     * Returns this device's type(Router, Switch, PBX, etc...).
     * 
     * @return the type
     */
    public Type getType() {
        return type;
    }

    /**
     * Sets this device's type(Router, Switch, PBX, etc...).
     * 
     * @param type
     *            the device type
     */
    public void setType(Type type) {
        Type oldType = getType();
        this.type = type;
        firePropertyChange(Prop.type, oldType, type);
    }

    /**
     * @return the protocolType
     */
    public CliProtocol getConnectionProtocolType() {
        return protocolType;
    }

    /**
     * @param protocolType
     *            the protocolType to set
     */
    public void setConnectionProtocolType(CliProtocol protocolType) {
        CliProtocol oldProtocolType = getConnectionProtocolType();
        this.protocolType = protocolType;
        firePropertyChange(Prop.connectionProtocolType, oldProtocolType, protocolType);
    }

    /**
     * @return the sysUpTime
     */
    public long getSysUpTime() {
        return sysUpTime;
    }

    /**
     * @param sysUpTime
     *            the sysUpTime to set
     */
    public void setSysUpTime(long sysUpTime) {
        long oldSysUpTime = getSysUpTime();
        this.sysUpTime = sysUpTime;
        firePropertyChange(Prop.sysUpTime, oldSysUpTime, sysUpTime);
    }

    /**
     * Returns this device's connection state(reachable/unreachable).
     * 
     * @return true if the device is reachable, false otherwise.
     */
    public boolean isReachable() {
        return isFlagSet(flags, REACHABLE);
    }

    /**
     * Sets this device's connection state(reachable/unreachable).
     * 
     * @param reachable
     *            the connection state.
     */
    public void setReachable(boolean reachable) {
        boolean oldReachable = isReachable();
        if (reachable != oldReachable) {
            flags ^= REACHABLE;
            firePropertyChange(Prop.reachable, oldReachable, reachable);
            firePropertyChange(Prop.deviceDownAlert, !oldReachable, !reachable);
        }
    }

    public boolean isDeviceDownAlert() {
        return !isReachable();
    }

    public void setDeviceDownAlert(boolean deviceDown) {
        setReachable(!deviceDown);
    }

    /**
     * @return the managed
     */
    public boolean isManaged() {
        return isFlagSet(flags, MANAGED);
    }

    /**
     * @param managed
     *            the managed to set
     */
    public void setManaged(boolean managed) {
        boolean oldManaged = isManaged();
        if (managed != oldManaged) {
            flags ^= MANAGED;
            firePropertyChange(Prop.managed, oldManaged, managed);
        }
    }

    /**
     * @return the managed
     */
    public boolean isPingable() {
        return isFlagSet(flags, PINGABLE);
    }

    /**
     * @param managed
     *            the managed to set
     */
    public void setPingable(boolean pingable) {
        boolean oldPingable = isPingable();
        if (pingable != oldPingable) {
            flags ^= PINGABLE;
            firePropertyChange(Prop.pingable, oldPingable, pingable);
        }
    }

    /**
     * @return the managed
     */
    public boolean isSnmpable() {
        return isFlagSet(flags, SNMPABLE);
    }

    /**
     * @param managed
     *            the managed to set
     */
    public void setSnmpable(boolean snmpable) {
        boolean oldSnmpable = isSnmpable();
        if (snmpable != oldSnmpable) {
            flags ^= SNMPABLE;
            firePropertyChange(Prop.snmpable, oldSnmpable, snmpable);
        }

        if (snmpable == false) {
            setWarning(false);
            for (Interface interf : getInterfaces()) {
                interf.setWarning(false);
                interf.setStatus(1);
            }
            setSnmpUnreachable(false);
        }
    }

    /**
     * @return the tracking stats state.
     */
    public boolean isTrackingStats() {
        return isFlagSet(flags, TRACKING_STATS);
    }

    /**
     * @param trackingStats
     *            the tracking stats state.
     */
    public void setTrackingStats(boolean trackingStats) {
        boolean oldTrackingStats = isTrackingStats();
        if (trackingStats != oldTrackingStats) {
            flags ^= TRACKING_STATS;
            firePropertyChange(Prop.trackingStats, oldTrackingStats, trackingStats);
        }
    }

    /**
     * @return the testTimeout
     */
    public int getTestTimeout() {
        return testTimeout;
    }

    /**
     * @param testTimeout
     *            the testTimeout to set
     */
    public void setTestTimeout(int testTimeout) {
        int oldTestTimeout = getTestTimeout();
        this.testTimeout = testTimeout;
        firePropertyChange(Prop.testTimeout, oldTestTimeout, testTimeout);
    }

    /**
     * @return the intensiveTestDuration
     */
    public int getIntensiveTestDuration() {
        return intensiveTestDuration;
    }

    /**
     * @param intensiveTestDuration
     *            the intensiveTestDuration to set
     */
    public void setIntensiveTestDuration(int intensiveTestDuration) {
        int oldIntensiveTestDuration = getIntensiveTestDuration();
        this.intensiveTestDuration = intensiveTestDuration;
        firePropertyChange(Prop.intensiveTestDuration, oldIntensiveTestDuration, intensiveTestDuration);
    }

    /**
     * @return the snmpTimeout
     */
    public int getSnmpTimeout() {
        return snmpTimeout;
    }

    /**
     * @param snmpTimeout
     *            the snmpTimeout to set
     */
    public void setSnmpTimeout(int snmpTimeout) {
        int oldSnmpTimeout = getSnmpTimeout();
        this.snmpTimeout = snmpTimeout;
        firePropertyChange(Prop.snmpTimeout, oldSnmpTimeout, snmpTimeout);
    }

    /**
     * @return the snmpRetries
     */
    public int getSnmpRetries() {
        return snmpRetries;
    }

    /**
     * @param snmpRetries
     *            the snmpRetries to set
     */
    public void setSnmpRetries(int snmpRetries) {
        int oldSnmpRetries = getSnmpRetries();
        this.snmpRetries = snmpRetries;
        firePropertyChange(Prop.snmpRetries, oldSnmpRetries, snmpRetries);
    }

    /**
     * Returns this device's ip address.
     * 
     * @return the ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * Sets this device's ip address.
     * 
     * @param ip
     *            the ip to set
     */
    public void setIp(String ip) {
        String oldIp = getIp();
        this.ip = ip;
        firePropertyChange(Prop.ip, oldIp, ip);
    }

    /**
     * @return the hostname
     */
    public String getHostname() {
        return hostname;
    }

    /**
     * @param hostname
     *            the hostname to set
     */
    public void setHostname(String hostname) {
        String oldHostname = getHostname();
        this.hostname = hostname;
        firePropertyChange(Prop.hostname, oldHostname, hostname);
    }

    /**
     * @return the alias
     */
    public String getAlias() {
        return alias;
    }

    /**
     * @param alias
     *            the alias to set
     */
    public void setAlias(String alias) {
        String oldAlias = getAlias();
        this.alias = alias;
        firePropertyChange(Prop.alias, oldAlias, alias);
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     *            the description to set
     */
    public void setDescription(String description) {
        String oldDescription = getDescription();
        this.description = description;
        firePropertyChange(Prop.description, oldDescription, description);
    }

    /**
     * @return the contact
     */
    public String getContact() {
        return contact;
    }

    /**
     * @param contact
     *            the contact to set
     */
    public void setContact(String contact) {
        String oldContact = getContact();
        this.contact = contact;
        firePropertyChange(Prop.contact, oldContact, contact);
    }

    /**
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * @param location
     *            the location to set
     */
    public void setLocation(String location) {
        String oldLocation = getLocation();
        this.location = location;
        firePropertyChange(Prop.location, oldLocation, location);
    }

    /**
     * @return the snmpPublicCommunity
     */
    public String getSnmpPublicCommunity() {
        return snmpPublicCommunity;
    }

    /**
     * @param snmpPublicCommunity
     *            the snmpPublicCommunity to set
     */
    public void setSnmpPublicCommunity(String snmpPublicCommunity) {
        String oldSnmpPublicCommunity = getSnmpPublicCommunity();
        this.snmpPublicCommunity = snmpPublicCommunity;
        firePropertyChange(Prop.snmpPublicCommunity, oldSnmpPublicCommunity, snmpPublicCommunity);
    }

    /**
     * @return the latencyMeasurementIp
     */
    public String getLatencyMeasurementIp() {
        return latencyMeasurementIp;
    }

    /**
     * @param latencyMeasurementIp
     *            the latencyMeasurementIp to set
     */
    public void setLatencyMeasurementIp(String latencyMeasurementIp) {
        String oldlatencyMeasurementIp = getLatencyMeasurementIp();
        this.latencyMeasurementIp = latencyMeasurementIp;
        firePropertyChange(Prop.latencyMeasurementIp, oldlatencyMeasurementIp, latencyMeasurementIp);
    }

    /**
     * @return the maxGetOIDS
     */
    public int getMaxGetSize() {
        return maxGetSize;
    }

    /**
     * @param maxGetOIDS
     *            the maxGetOIDS to set
     */
    public void setMaxGetSize(int maxGetSize) {
        int oldMaxGetSize = getMaxGetSize();
        this.maxGetSize = maxGetSize;
        firePropertyChange(Prop.maxGetSize, oldMaxGetSize, maxGetSize);
    }

    /**
     * @return the lastUpdateTime
     */
    public long getLastUpdateTime() {
        return lastUpdateTime;
    }

    /**
     * @param lastUpdateTime
     *            the lastUpdateTime to set
     */
    public void setLastUpdateTime(long lastUpdateTime) {
        long oldLastUpdateTime = getLastUpdateTime();
        this.lastUpdateTime = lastUpdateTime;
        firePropertyChange(Prop.lastUpdateTime, oldLastUpdateTime, lastUpdateTime);
    }

    /**
     * @return the statsTableId
     */
    public String getStatsTableId() {
        return statsTableId;
    }

    /**
     * @param statsTableId
     *            the statsTableId to set
     */
    public void setStatsTableId(String statsTableId) {
        String oldStatsTableId = getStatsTableId();
        this.statsTableId = statsTableId;
        firePropertyChange(Prop.statsTableId, oldStatsTableId, statsTableId);
    }

    /**
     * @return the reachableByRedundantNms
     */
    public boolean isReachableByRedundantNms() {
        return isFlagSet(flags, REACHABLE_BY_REDUNDANT);
    }

    /**
     * @param reachableByRedundantNms
     *            the reachableByRedundantNms to set
     */
    public void setReachableByRedundantNms(boolean reachableByRedundantNms) {
        boolean oldReachableByRedundant = isReachableByRedundantNms();
        if (reachableByRedundantNms != oldReachableByRedundant) {
            flags ^= REACHABLE_BY_REDUNDANT;
            firePropertyChange(Prop.reachableByRedundantNms, oldReachableByRedundant, reachableByRedundantNms);
        }
    }

    // /**
    // * @return the graphAttributes
    // */
    // public JGraphAttributes getGraphAttributes() {
    // return graphAttributes;
    // }

    // /**
    // * @param graphAttributes
    // * the graphAttributes to set
    // */
    // public void setGraphAttributes(JGraphAttributes graphAttributes) {
    // JGraphAttributes oldGraphAttributes = getGraphAttributes();
    // this.graphAttributes = graphAttributes;
    // firePropertyChange(Prop.graphAttributes, oldGraphAttributes,
    // graphAttributes);
    // }

    /**
     * Returns a copy of the {@link Set} that holds this device's network
     * interfaces.
     * 
     * @return the network interfaces.
     */
    public Set<Interface> getInterfaces() {
        return new HashSet<Interface>(interfaces);
    }

    /**
     * Adds new interfaces to this device.
     * 
     * @param interfaces
     *            the network interfaces.
     */
    public void addInterfaces(Set<Interface> interfaces) {
        if (interfaces != null && !interfaces.isEmpty()) {
            for (Interface iface : interfaces) {
                iface.setDevice(this);
                this.interfaces.add(iface);
            }
            firePropertyChange(Prop.addInterfaces, null, interfaces);
        }
    }

    /**
     * Removes interfaces from this device.
     * 
     * @param interfaces
     *            the network interfaces.
     */
    public void removeInterfaces(Set<Interface> interfaces) {
        if (interfaces != null && !interfaces.isEmpty()) {
            for (Interface iface : interfaces) {
                this.interfaces.remove(iface);
                iface.setDevice(null);
            }
            firePropertyChange(Prop.removeInterfaces, null, interfaces);
        }
    }

    /**
     * Returns this device's interfaces as a {@link Map} where the key is the
     * {@link Interface}'s {@link Interface#getIfIndex()}.
     * 
     * @return a {@link Map} with the {@link Interface}s.
     */
    public Map<Integer, Interface> getInterfacesByIfIndex() {
        return Interfaces.byIfIndex(interfaces);
    }

    /**
     * Returns a copy of the {@link Set} that holds this device's links.
     * 
     * @return the links with other devices.
     */
    @Override
    public Set<Link> getLinks() {
        return new HashSet<Link>(links);
    }

    /**
     * Adds new links to this device.
     * 
     * @param links
     *            the links.
     */
    @Override
    public void addLinks(Set<Link> links) {
        if (links != null && !links.isEmpty()) {
            this.links.addAll(links);
            firePropertyChange(Prop.addLinks, null, links);
        }
    }

    /**
     * Removes links from this device.
     * 
     * @param links
     *            the links.
     */
    @Override
    public void removeLinks(Set<Link> links) {
        if (links != null && !links.isEmpty()) {
            this.links.removeAll(links);
            firePropertyChange(Prop.removeLinks, null, links);
        }
    }

    /**
     * Returns a copy of the {@link Set} that holds this device's IP SLAs.
     * 
     * @return the IP SLAs.
     */
    public Set<IpSla> getIpSlas() {
        return new HashSet<IpSla>(ipSlas);
    }

    /**
     * Adds new IP SLAs to this device.
     * 
     * @param ipSlas
     *            the IP SLAs.
     */
    public void addIpSlas(Set<IpSla> ipSlas) {
        if (ipSlas != null && !ipSlas.isEmpty()) {
            for (IpSla ipSla : ipSlas) {
                ipSla.setDevice(this);
                this.ipSlas.add(ipSla);
            }
            firePropertyChange(Prop.addIpSlas, null, ipSlas);
        }
    }

    /**
     * Removes IP SLAs from this device.
     * 
     * @param ipSlas
     *            the IP SLAs.
     */
    public void removeIpSlas(Set<IpSla> ipSlas) {
        if (ipSlas != null && !ipSlas.isEmpty()) {
            for (IpSla ipSla : ipSlas) {
                this.ipSlas.remove(ipSla);
                ipSla.setDevice(null);
            }
            firePropertyChange(Prop.removeIpSlas, null, ipSlas);
        }
    }

    /**
     * @return the snmpVersion
     */
    public SnmpVersion getSnmpVersion() {
        return snmpVersion;
    }

    /**
     * @param snmpVersion
     *            the snmpVersion to set
     */
    public void setSnmpVersion(SnmpVersion snmpVersion) {
        SnmpVersion oldSnmpVersion = getSnmpVersion();
        this.snmpVersion = snmpVersion;
        firePropertyChange(Prop.snmpVersion, oldSnmpVersion, snmpVersion);
    }

    /**
     * @return the snmpUser
     */
    public String getSnmpUser() {
        return snmpUser;
    }

    /**
     * @param snmpUser
     *            the snmpUser to set
     */
    public void setSnmpUser(String snmpUser) {
        String oldSnmpUser = getSnmpUser();
        this.snmpUser = snmpUser;
        firePropertyChange(Prop.snmpUser, oldSnmpUser, snmpUser);
    }

    /**
     * @return the authenticationProtocol
     */
    public String getAuthenticationProtocol() {
        return authenticationProtocol;
    }

    /**
     * @param authenticationProtocol
     *            the authenticationProtocol to set
     */
    public void setAuthenticationProtocol(String authenticationProtocol) {
        String oldAuthenticationProtocol = getAuthenticationProtocol();
        this.authenticationProtocol = authenticationProtocol;
        firePropertyChange(Prop.authenticationProtocol, oldAuthenticationProtocol, authenticationProtocol);
    }

    /**
     * @return the securityLevel
     */
    public int getSecurityLevel() {
        return securityLevel;
    }

    /**
     * @param securityLevel
     *            the securityLevel to set
     */
    public void setSecurityLevel(int securityLevel) {
        int oldSecutiryLevel = getSecurityLevel();
        this.securityLevel = securityLevel;
        firePropertyChange(Prop.securityLevel, oldSecutiryLevel, securityLevel);
    }

    /**
     * @return the telnetUser
     */
    public String getTelnetUser() {
        return telnetUser;
    }

    /**
     * @param telnetUser
     *            the telnetUser to set
     */
    public void setTelnetUser(String telnetUser) {
        String oldTelnetUser = getTelnetUser();
        this.telnetUser = telnetUser;
        firePropertyChange(Prop.telnetUser, oldTelnetUser, telnetUser);
    }

    /**
     * @return the telnetPassword
     */
    public String getTelnetPassword() {
        return telnetPassword;
    }

    /**
     * @param telnetPassword
     *            the telnetPassword to set
     */
    public void setTelnetPassword(String telnetPassword) {
        String oldTelnetPassword = getTelnetPassword();
        this.telnetPassword = telnetPassword;
        firePropertyChange(Prop.telnetPassword, oldTelnetPassword, telnetPassword);
    }

    /**
     * @return telneteable
     */
    public boolean isTelnetable() {
        return isFlagSet(flags, TELNETABLE);
    }

    /**
     * sets if the device is telneteable
     * 
     * @param telnet
     */
    public void setTelnetable(boolean telnet) {
        boolean oldTelnetable = isTelnetable();
        if (telnet != oldTelnetable) {
            flags ^= TELNETABLE;
            firePropertyChange(Prop.telnetable, oldTelnetable, telnet);
        }
    }

    /**
     * @return the privilegeModePassword
     */
    public String getPrivilegeModePassword() {
        return privilegeModePassword;
    }

    /**
     * @param privilegeModePassword
     *            the privilegeModePassword to set
     */
    public void setPrivilegeModePassword(String privilegeModePassword) {
        String oldPrivilegeModePassword = getPrivilegeModePassword();
        this.privilegeModePassword = privilegeModePassword;
        firePropertyChange(Prop.privilegeModePassword, oldPrivilegeModePassword, privilegeModePassword);
    }

    /**
     * returns if the device is saving hardware inventory (only Cisco Devices)
     * 
     * @return isSavingInventory
     */
    public boolean isInventory() {
        return isFlagSet(flags, INVENTORY);
    }

    /**
     * 
     * @param inventory
     */
    public void setInventory(boolean inventory) {
        boolean oldInventory = isInventory();
        if (inventory != oldInventory) {
            flags ^= INVENTORY;
            firePropertyChange(Prop.inventory, oldInventory, inventory);
        }
    }

    /**
     * @return the warning
     */
    public boolean isWarning() {
        return isFlagSet(flags, WARNING);
    }

    /**
     * @param warning
     *            the warning to set
     */
    public void setWarning(boolean warning) {
        boolean oldWarning = isWarning();
        if (warning != oldWarning) {
            flags ^= WARNING;
            firePropertyChange(Prop.warning, oldWarning, warning);
        }
    }

    public boolean isSnmpUnreachable() {
        return isFlagSet(flags, SNMP_UNREACHABLE);
    }

    public void setSnmpUnreachable(boolean snmpUnreachable) {
        boolean oldSNMPUnreachable = isSnmpUnreachable();
        if (snmpUnreachable != oldSNMPUnreachable) {
            flags ^= SNMP_UNREACHABLE;
            firePropertyChange(Prop.snmpUnreachable, oldSNMPUnreachable, snmpUnreachable);
        }
    }

    public boolean isUpsOnBattery() {
        return isFlagSet(flags, UPS_ON_BATTERY);
    }

    public void setUpsOnBattery(boolean upsOnBattery) {
        boolean oldUPSOnBattery = isUpsOnBattery();
        if (upsOnBattery != oldUPSOnBattery) {
            flags ^= UPS_ON_BATTERY;
            firePropertyChange(Prop.upsOnBattery, oldUPSOnBattery, upsOnBattery);
        }
    }

    public boolean isUpsRemainingCharge() {
        return isFlagSet(flags, UPS_REMAINING_CHARGE);
    }

    public void setUpsRemainingCharge(boolean upsRemainingCharge) {
        boolean oldUpsRemainingCharge = isUpsRemainingCharge();
        if (upsRemainingCharge != oldUpsRemainingCharge) {
            flags ^= UPS_REMAINING_CHARGE;
            firePropertyChange(Prop.upsRemainingCharge, oldUpsRemainingCharge, upsRemainingCharge);
        }
    }

    public boolean isUpsBatteryStatus() {
        return isFlagSet(flags, UPS_BATTERY_STATUS);
    }

    public void setUpsBatteryStatus(boolean upsBatteryStatus) {
        boolean oldUpsBatteryStatus = isUpsBatteryStatus();
        if (upsBatteryStatus != oldUpsBatteryStatus) {
            flags ^= UPS_BATTERY_STATUS;
            firePropertyChange(Prop.upsBatteryStatus, oldUpsBatteryStatus, upsBatteryStatus);
        }
    }

    public boolean isDeviceConfChanged() {
        return isFlagSet(flags, DEVICE_CONFIG);
    }

    public void setDeviceConfChanged(boolean deviceConfChanged) {
        boolean oldDeviceConfChanged = isDeviceConfChanged();
        if (deviceConfChanged != oldDeviceConfChanged) {
            flags ^= DEVICE_CONFIG;
            firePropertyChange(Prop.deviceConfChanged, oldDeviceConfChanged, deviceConfChanged);
        }
    }

    public boolean isSyslogSeverity0() {
        return isFlagSet(flags, SYSLOG_SEVERITY_0);
    }

    public void setSyslogSeverity0(boolean syslogSeverity0) {
        boolean oldSyslogSeverity0 = isSyslogSeverity0();
        if (syslogSeverity0 != oldSyslogSeverity0) {
            flags ^= SYSLOG_SEVERITY_0;
            firePropertyChange(Prop.syslogSeverity0, oldSyslogSeverity0, syslogSeverity0);
        }
    }

    public boolean isSyslogSeverity1() {
        return isFlagSet(flags, SYSLOG_SEVERITY_1);
    }

    public void setSyslogSeverity1(boolean syslogSeverity1) {
        boolean oldSyslogSeverity1 = isSyslogSeverity1();
        if (syslogSeverity1 != oldSyslogSeverity1) {
            flags ^= SYSLOG_SEVERITY_1;
            firePropertyChange(Prop.syslogSeverity1, oldSyslogSeverity1, syslogSeverity1);
        }
    }

    public boolean isSyslogSeverity2() {
        return isFlagSet(flags, SYSLOG_SEVERITY_2);
    }

    public void setSyslogSeverity2(boolean syslogSeverity2) {
        boolean oldSyslogSeverity2 = isSyslogSeverity2();
        if (syslogSeverity2 != oldSyslogSeverity2) {
            flags ^= SYSLOG_SEVERITY_2;
            firePropertyChange(Prop.syslogSeverity2, oldSyslogSeverity2, syslogSeverity2);
        }
    }

    public boolean isSyslogSeverity3() {
        return isFlagSet(flags, SYSLOG_SEVERITY_3);
    }

    public void setSyslogSeverity3(boolean syslogSeverity3) {
        boolean oldSyslogSeverity3 = isSyslogSeverity3();
        if (syslogSeverity3 != oldSyslogSeverity3) {
            flags ^= SYSLOG_SEVERITY_3;
            firePropertyChange(Prop.syslogSeverity3, oldSyslogSeverity3, syslogSeverity3);
        }
    }

    public boolean isSyslogSeverity4() {
        return isFlagSet(flags, SYSLOG_SEVERITY_4);
    }

    public void setSyslogSeverity4(boolean syslogSeverity4) {
        boolean oldSyslogSeverity4 = isSyslogSeverity4();
        if (syslogSeverity4 != oldSyslogSeverity4) {
            flags ^= SYSLOG_SEVERITY_4;
            firePropertyChange(Prop.syslogSeverity4, oldSyslogSeverity4, syslogSeverity4);
        }
    }

    public boolean isSyslogSeverity5() {
        return isFlagSet(flags, SYSLOG_SEVERITY_5);
    }

    public void setSyslogSeverity5(boolean syslogSeverity5) {
        boolean oldSyslogSeverity5 = isSyslogSeverity5();
        if (syslogSeverity5 != oldSyslogSeverity5) {
            flags ^= SYSLOG_SEVERITY_5;
            firePropertyChange(Prop.syslogSeverity5, oldSyslogSeverity5, syslogSeverity5);
        }
    }

    public boolean isSyslogSeverity6() {
        return isFlagSet(flags, SYSLOG_SEVERITY_6);
    }

    public void setSyslogSeverity6(boolean syslogSeverity6) {
        boolean oldSyslogSeverity6 = isSyslogSeverity6();
        if (syslogSeverity6 != oldSyslogSeverity6) {
            flags ^= SYSLOG_SEVERITY_6;
            firePropertyChange(Prop.syslogSeverity6, oldSyslogSeverity6, syslogSeverity6);
        }
    }

    public boolean isSyslogSeverity7() {
        return isFlagSet(flags, SYSLOG_SEVERITY_7);
    }

    public void setSyslogSeverity7(boolean syslogSeverity7) {
        boolean oldSyslogSeverity7 = isSyslogSeverity7();
        if (syslogSeverity7 != oldSyslogSeverity7) {
            flags ^= SYSLOG_SEVERITY_7;
            firePropertyChange(Prop.syslogSeverity7, oldSyslogSeverity7, syslogSeverity7);
        }
    }

    public boolean isSharingPingConfiguration() {
        return isFlagSet(configurationFlags, SHARED_PING_CONFIG);
    }

    public void setSharingPingConfiguration(boolean sharingPingConfiguration) {
        boolean oldSharingPingConfiguration = isSharingPingConfiguration();
        if (sharingPingConfiguration != oldSharingPingConfiguration) {
            configurationFlags ^= SHARED_PING_CONFIG;
            firePropertyChange(Prop.sharingPingConfiguration, oldSharingPingConfiguration, sharingPingConfiguration);
        }
    }

    public boolean isSharingSnmpConfiguration() {
        return isFlagSet(configurationFlags, SHARED_SNMP_CONFIG);
    }

    public void setSharingSnmpConfiguration(boolean sharingSnmpConfiguration) {
        boolean oldSharingSnmpConfiguration = isSharingSnmpConfiguration();
        if (sharingSnmpConfiguration != oldSharingSnmpConfiguration) {
            configurationFlags ^= SHARED_SNMP_CONFIG;
            firePropertyChange(Prop.sharingSnmpConfiguration, oldSharingSnmpConfiguration, sharingSnmpConfiguration);
        }
    }

    public boolean isSharingCliConfiguration() {
        return isFlagSet(configurationFlags, SHARED_CLI_CONFIG);
    }

    public void setSharingCliConfiguration(boolean sharingCliConfiguration) {
        boolean oldSharingCliConfiguration = isSharingCliConfiguration();
        if (sharingCliConfiguration != oldSharingCliConfiguration) {
            configurationFlags ^= SHARED_CLI_CONFIG;
            firePropertyChange(Prop.sharingCliConfiguration, oldSharingCliConfiguration, sharingCliConfiguration);
        }
    }

    public boolean isSharingTrapsConfiguration() {
        return isFlagSet(configurationFlags, SHARED_TRAPS_CONFIG);
    }

    public void setSharingTrapsConfiguration(boolean sharingTrapsConfiguration) {
        boolean oldSharingTrapsConfiguration = isSharingTrapsConfiguration();
        if (sharingTrapsConfiguration != oldSharingTrapsConfiguration) {
            configurationFlags ^= SHARED_TRAPS_CONFIG;
            firePropertyChange(Prop.sharingTrapsConfiguration, oldSharingTrapsConfiguration, sharingTrapsConfiguration);
        }
    }

    public boolean isSyslog0Enabled() {
        return isFlagSet(configurationFlags, SYSLOG_0_ENABLED);
    }

    public void setSyslog0Enabled(boolean syslog0Enabled) {
        boolean oldSyslog0Enabled = isSyslog0Enabled();
        if (syslog0Enabled != oldSyslog0Enabled) {
            configurationFlags ^= SYSLOG_0_ENABLED;
            firePropertyChange(Prop.syslog0Enabled, oldSyslog0Enabled, syslog0Enabled);
        }
    }

    public boolean isSyslog1Enabled() {
        return isFlagSet(configurationFlags, SYSLOG_1_ENABLED);
    }

    public void setSyslog1Enabled(boolean syslog1Enabled) {
        boolean oldSyslog1Enabled = isSyslog1Enabled();
        if (syslog1Enabled != oldSyslog1Enabled) {
            configurationFlags ^= SYSLOG_1_ENABLED;
            firePropertyChange(Prop.syslog1Enabled, oldSyslog1Enabled, syslog1Enabled);
        }
    }

    public boolean isSyslog2Enabled() {
        return isFlagSet(configurationFlags, SYSLOG_2_ENABLED);
    }

    public void setSyslog2Enabled(boolean syslog2Enabled) {
        boolean oldSyslog2Enabled = isSyslog2Enabled();
        if (syslog2Enabled != oldSyslog2Enabled) {
            configurationFlags ^= SYSLOG_2_ENABLED;
            firePropertyChange(Prop.syslog2Enabled, oldSyslog2Enabled, syslog2Enabled);
        }
    }

    public boolean isSyslog3Enabled() {
        return isFlagSet(configurationFlags, SYSLOG_3_ENABLED);
    }

    public void setSyslog3Enabled(boolean syslog3Enabled) {
        boolean oldSyslog3Enabled = isSyslog3Enabled();
        if (syslog3Enabled != oldSyslog3Enabled) {
            configurationFlags ^= SYSLOG_3_ENABLED;
            firePropertyChange(Prop.syslog3Enabled, oldSyslog3Enabled, syslog3Enabled);
        }
    }

    public boolean isSyslog4Enabled() {
        return isFlagSet(configurationFlags, SYSLOG_4_ENABLED);
    }

    public void setSyslog4Enabled(boolean syslog4Enabled) {
        boolean oldSyslog4Enabled = isSyslog4Enabled();
        if (syslog4Enabled != oldSyslog4Enabled) {
            configurationFlags ^= SYSLOG_4_ENABLED;
            firePropertyChange(Prop.syslog4Enabled, oldSyslog4Enabled, syslog4Enabled);
        }
    }

    public boolean isSyslog5Enabled() {
        return isFlagSet(configurationFlags, SYSLOG_5_ENABLED);
    }

    public void setSyslog5Enabled(boolean syslog5Enabled) {
        boolean oldSyslog5Enabled = isSyslog5Enabled();
        if (syslog5Enabled != oldSyslog5Enabled) {
            configurationFlags ^= SYSLOG_5_ENABLED;
            firePropertyChange(Prop.syslog5Enabled, oldSyslog5Enabled, syslog5Enabled);
        }
    }

    public boolean isSyslog6Enabled() {
        return isFlagSet(configurationFlags, SYSLOG_6_ENABLED);
    }

    public void setSyslog6Enabled(boolean syslog6Enabled) {
        boolean oldSyslog6Enabled = isSyslog6Enabled();
        if (syslog6Enabled != oldSyslog6Enabled) {
            configurationFlags ^= SYSLOG_6_ENABLED;
            firePropertyChange(Prop.syslog6Enabled, oldSyslog6Enabled, syslog6Enabled);
        }
    }

    public boolean isSyslog7Enabled() {
        return isFlagSet(configurationFlags, SYSLOG_7_ENABLED);
    }

    public void setSyslog7Enabled(boolean syslog7Enabled) {
        boolean oldSyslog7Enabled = isSyslog7Enabled();
        if (syslog7Enabled != oldSyslog7Enabled) {
            configurationFlags ^= SYSLOG_7_ENABLED;
            firePropertyChange(Prop.syslog7Enabled, oldSyslog7Enabled, syslog7Enabled);
        }
    }

    public boolean isTrapsEnabled() {
        return isFlagSet(configurationFlags, TRAPS_ENABLED);
    }

    public void setTrapsEnabled(boolean trapsEnabled) {
        boolean oldTrapsEnabled = isTrapsEnabled();
        if (trapsEnabled != oldTrapsEnabled) {
            configurationFlags ^= TRAPS_ENABLED;
            firePropertyChange(Prop.trapsEnabled, oldTrapsEnabled, trapsEnabled);
        }
    }

    public boolean isSyslogEnabled(int severity) {
        switch (severity) {
            case 0:
                return isSyslog0Enabled();
            case 1:
                return isSyslog1Enabled();
            case 2:
                return isSyslog2Enabled();
            case 3:
                return isSyslog3Enabled();
            case 4:
                return isSyslog4Enabled();
            case 5:
                return isSyslog5Enabled();
            case 6:
                return isSyslog6Enabled();
            case 7:
                return isSyslog7Enabled();
        }
        return false;
    }

    /* Riverbed Steelhead */

    public boolean isSteelheadSystemHealth() {
        return isFlagSet(steelheadFlags, STEELHEAD_SYSTEM_HEALTH);
    }

    public void setSteelheadSystemHealth(boolean steelheadSystemHealth) {
        boolean oldSteelheadSystemHealth = isSteelheadSystemHealth();

        if (steelheadSystemHealth != oldSteelheadSystemHealth) {
            steelheadFlags ^= STEELHEAD_SYSTEM_HEALTH;
            firePropertyChange(Prop.steelheadSystemHealth, oldSteelheadSystemHealth, steelheadSystemHealth);
        }
    }

    public boolean isSteelheadOptServiceStatus() {
        return isFlagSet(steelheadFlags, STEELHEAD_OPT_SERVICE_STATUS);
    }

    public void setSteelheadOptServiceStatus(boolean steelheadOptServiceStatus) {
        boolean oldSteelheadOptServiceStatus = isSteelheadOptServiceStatus();

        if (steelheadOptServiceStatus != oldSteelheadOptServiceStatus) {
            steelheadFlags ^= STEELHEAD_OPT_SERVICE_STATUS;
            firePropertyChange(Prop.steelheadOptServiceStatus, oldSteelheadOptServiceStatus, steelheadOptServiceStatus);
        }
    }

    public boolean isSteelheadCpuUtil() {
        return isFlagSet(steelheadFlags, STEELHEAD_CPU_UTIL);
    }

    public void setSteelheadCpuUtil(boolean steelheadCpuUtil) {
        boolean oldSteelheadCpuUtil = isSteelheadCpuUtil();

        if (steelheadCpuUtil != oldSteelheadCpuUtil) {
            steelheadFlags ^= STEELHEAD_CPU_UTIL;
            firePropertyChange(Prop.steelheadCpuUtil, oldSteelheadCpuUtil, steelheadCpuUtil);
        }
    }

    public boolean isSteelheadBypassMode() {
        return isFlagSet(steelheadFlags, STEELHEAD_BYPASS_MODE);
    }

    public void setSteelheadBypassMode(boolean steelheadBypassMode) {
        boolean oldSteelheadBypassMode = isSteelheadBypassMode();

        if (steelheadBypassMode != oldSteelheadBypassMode) {
            steelheadFlags ^= STEELHEAD_BYPASS_MODE;
            firePropertyChange(Prop.steelheadBypassMode, oldSteelheadBypassMode, steelheadBypassMode);
        }
    }

    public boolean isSteelheadAdmissionMemError() {
        return isFlagSet(steelheadFlags, STEELHEAD_ADMISSION_MEM_ERROR);
    }

    public void setSteelheadAdmissionMemError(boolean steelheadAdmissionMemError) {
        boolean oldSteelheadAdmissionMemError = isSteelheadAdmissionMemError();

        if (steelheadAdmissionMemError != oldSteelheadAdmissionMemError) {
            steelheadFlags ^= STEELHEAD_ADMISSION_MEM_ERROR;
            firePropertyChange(Prop.steelheadAdmissionMemError, oldSteelheadAdmissionMemError,
                steelheadAdmissionMemError);
        }
    }

    public boolean isSteelheadAdmissionConnError() {
        return isFlagSet(steelheadFlags, STEELHEAD_ADMISSION_CONN_ERROR);
    }

    public void setSteelheadAdmissionConnError(boolean steelheadAdmissionConnError) {
        boolean oldSteelheadAdmissionConnError = isSteelheadAdmissionConnError();

        if (steelheadAdmissionConnError != oldSteelheadAdmissionConnError) {
            steelheadFlags ^= STEELHEAD_ADMISSION_CONN_ERROR;
            firePropertyChange(Prop.steelheadAdmissionConnError, oldSteelheadAdmissionConnError,
                steelheadAdmissionConnError);
        }
    }

    public boolean isSteelheadHaltError() {
        return isFlagSet(steelheadFlags, STEELHEAD_HALT_ERROR);
    }

    public void setSteelheadHaltError(boolean steelheadHaltError) {
        boolean oldSteelheadHaltError = isSteelheadHaltError();

        if (steelheadHaltError != oldSteelheadHaltError) {
            steelheadFlags ^= STEELHEAD_HALT_ERROR;
            firePropertyChange(Prop.steelheadHaltError, oldSteelheadHaltError, steelheadHaltError);
        }
    }

    public boolean isSteelheadServiceError() {
        return isFlagSet(steelheadFlags, STEELHEAD_SERVICE_ERROR);
    }

    public void setSteelheadServiceError(boolean steelheadServiceError) {
        boolean oldSteelheadServiceError = isSteelheadServiceError();

        if (steelheadServiceError != oldSteelheadServiceError) {
            steelheadFlags ^= STEELHEAD_SERVICE_ERROR;
            firePropertyChange(Prop.steelheadServiceError, oldSteelheadServiceError, steelheadServiceError);
        }
    }

    public boolean isSteelheadLinkError() {
        return isFlagSet(steelheadFlags, STEELHEAD_LINK_ERROR);
    }

    public void setSteelheadLinkError(boolean steelheadLinkError) {
        boolean oldSteelheadLinkError = isSteelheadLinkError();

        if (steelheadLinkError != oldSteelheadLinkError) {
            steelheadFlags ^= STEELHEAD_LINK_ERROR;
            firePropertyChange(Prop.steelheadLinkError, oldSteelheadLinkError, steelheadLinkError);
        }
    }

    public boolean isSteelheadAsymRouteError() {
        return isFlagSet(steelheadFlags, STEELHEAD_ASYM_ROUTE_ERROR);
    }

    public void setSteelheadAsymRouteError(boolean steelheadAsymRouteError) {
        boolean oldSteelheadAsymRouteError = isSteelheadAsymRouteError();

        if (steelheadAsymRouteError != oldSteelheadAsymRouteError) {
            steelheadFlags ^= STEELHEAD_ASYM_ROUTE_ERROR;
            firePropertyChange(Prop.steelheadAsymRouteError, oldSteelheadAsymRouteError, steelheadAsymRouteError);
        }
    }

    public boolean isSteelheadTemperatureWarning() {
        return isFlagSet(steelheadFlags, STEELHEAD_TEMPERATURE_WARNING);
    }

    public void setSteelheadTemperatureWarning(boolean steelheadTemperatureWarning) {
        boolean oldSteelheadTemperatureWarning = isSteelheadTemperatureWarning();

        if (steelheadTemperatureWarning != oldSteelheadTemperatureWarning) {
            steelheadFlags ^= STEELHEAD_TEMPERATURE_WARNING;
            firePropertyChange(Prop.steelheadTemperatureWarning, oldSteelheadTemperatureWarning,
                steelheadTemperatureWarning);
        }
    }

    public boolean isSteelheadTemperatureCritical() {
        return isFlagSet(steelheadFlags, STEELHEAD_TEMPERATURE_CRITICAL);
    }

    public void setSteelheadTemperatureCritical(boolean steelheadTemperatureCritical) {
        boolean oldSteelheadTemperatureCritical = isSteelheadTemperatureCritical();

        if (steelheadTemperatureCritical != oldSteelheadTemperatureCritical) {
            steelheadFlags ^= STEELHEAD_TEMPERATURE_CRITICAL;
            firePropertyChange(Prop.steelheadTemperatureCritical, oldSteelheadTemperatureCritical,
                steelheadTemperatureCritical);
        }
    }

    public boolean isSteelheadDiskError() {
        return isFlagSet(steelheadFlags, STEELHEAD_DISK_ERROR);
    }

    public void setSteelheadDiskError(boolean steelheadDiskError) {
        boolean oldSteelheadDiskError = isSteelheadDiskError();

        if (steelheadDiskError != oldSteelheadDiskError) {
            steelheadFlags ^= STEELHEAD_DISK_ERROR;
            firePropertyChange(Prop.steelheadDiskError, oldSteelheadDiskError, steelheadDiskError);
        }
    }

    public boolean isSteelheadAdmissionCpuError() {
        return isFlagSet(steelheadFlags, STEELHEAD_ADMISSION_CPU_ERROR);
    }

    public void setSteelheadAdmissionCpuError(boolean steelheadAdmissionCpuError) {
        boolean oldSteelheadAdmissionCpuError = isSteelheadAdmissionCpuError();

        if (steelheadAdmissionCpuError != oldSteelheadAdmissionCpuError) {
            steelheadFlags ^= STEELHEAD_ADMISSION_CPU_ERROR;
            firePropertyChange(Prop.steelheadAdmissionCpuError, oldSteelheadAdmissionCpuError,
                steelheadAdmissionCpuError);
        }
    }

    public boolean isSteelheadAdmissionTcpError() {
        return isFlagSet(steelheadFlags, STEELHEAD_ADMISSION_TCP_ERROR);
    }

    public void setSteelheadAdmissionTcpError(boolean steelheadAdmissionTcpError) {
        boolean oldSteelheadAdmissionTcpError = isSteelheadAdmissionTcpError();

        if (steelheadAdmissionTcpError != oldSteelheadAdmissionTcpError) {
            steelheadFlags ^= STEELHEAD_ADMISSION_TCP_ERROR;
            firePropertyChange(Prop.steelheadAdmissionTcpError, oldSteelheadAdmissionTcpError,
                steelheadAdmissionTcpError);
        }
    }

    public boolean isSteelheadSystemDiskFullError() {
        return isFlagSet(steelheadFlags, STEELHEAD_SYSTEM_DISK_FULL_ERROR);
    }

    public void setSteelheadSystemDiskFullError(boolean steelheadSystemDiskFullError) {
        boolean oldSteelheadSystemDiskFullError = isSteelheadSystemDiskFullError();

        if (steelheadSystemDiskFullError != oldSteelheadSystemDiskFullError) {
            steelheadFlags ^= STEELHEAD_SYSTEM_DISK_FULL_ERROR;
            firePropertyChange(Prop.steelheadSystemDiskFullError, oldSteelheadSystemDiskFullError,
                steelheadSystemDiskFullError);
        }
    }

    public boolean isSteelheadCertsExpiringError() {
        return isFlagSet(steelheadFlags, STEELHEAD_CERTS_EXPIRING_ERROR);
    }

    public void setSteelheadCertsExpiringError(boolean steelheadCertsExpiringError) {
        boolean oldSteelheadCertsExpiringError = isSteelheadCertsExpiringError();

        if (steelheadCertsExpiringError != oldSteelheadCertsExpiringError) {
            steelheadFlags ^= STEELHEAD_CERTS_EXPIRING_ERROR;
            firePropertyChange(Prop.steelheadCertsExpiringError, oldSteelheadCertsExpiringError,
                steelheadCertsExpiringError);
        }
    }

    public boolean isSteelheadLicenseError() {
        return isFlagSet(steelheadFlags, STEELHEAD_LICENSE_ERROR);
    }

    public void setSteelheadLicenseError(boolean steelheadLicenseError) {
        boolean oldSteelheadLicenseError = isSteelheadLicenseError();

        if (steelheadLicenseError != oldSteelheadLicenseError) {
            steelheadFlags ^= STEELHEAD_LICENSE_ERROR;
            firePropertyChange(Prop.steelheadLicenseError, oldSteelheadLicenseError, steelheadLicenseError);
        }
    }

    public boolean isSteelheadHardwareError() {
        return isFlagSet(steelheadFlags, STEELHEAD_HARDWARE_ERROR);
    }

    public void setSteelheadHardwareError(boolean steelheadHardwareError) {
        boolean oldSteelheadHardwareError = isSteelheadHardwareError();

        if (steelheadHardwareError != oldSteelheadHardwareError) {
            steelheadFlags ^= STEELHEAD_HARDWARE_ERROR;
            firePropertyChange(Prop.steelheadHardwareError, oldSteelheadHardwareError, steelheadHardwareError);
        }
    }

    public boolean isSteelheadLanWanLoopError() {
        return isFlagSet(steelheadFlags, STEELHEAD_LAN_WAN_LOOP_ERROR);
    }

    public void setSteelheadLanWanLoopError(boolean steelheadLanWanLoopError) {
        boolean oldSteelheadLanWanLoopError = isSteelheadLanWanLoopError();

        if (steelheadLanWanLoopError != oldSteelheadLanWanLoopError) {
            steelheadFlags ^= STEELHEAD_LAN_WAN_LOOP_ERROR;
            firePropertyChange(Prop.steelheadLanWanLoopError, oldSteelheadLanWanLoopError, steelheadLanWanLoopError);
        }
    }

    public boolean isSteelheadOptimizationServiceStatusError() {
        return isFlagSet(steelheadFlags, STEELHEAD_OPTIMIZATION_SERVICE_STATUS_ERROR);
    }

    public void setSteelheadOptimizationServiceStatusError(boolean steelheadOptimizationServiceStatusError) {
        boolean oldSteelheadOptimizationServiceStatusError = isSteelheadOptimizationServiceStatusError();

        if (steelheadOptimizationServiceStatusError != oldSteelheadOptimizationServiceStatusError) {
            steelheadFlags ^= STEELHEAD_OPTIMIZATION_SERVICE_STATUS_ERROR;
            firePropertyChange(Prop.steelheadOptimizationServiceStatusError,
                oldSteelheadOptimizationServiceStatusError, steelheadOptimizationServiceStatusError);
        }
    }

    public boolean isSteelheadLicenseExpiring() {
        return isFlagSet(steelheadFlags, STEELHEAD_LICENSE_EXPIRING);
    }

    public void setSteelheadLicenseExpiring(boolean steelheadLicenseExpiring) {
        boolean oldSteelheadLicenseExpiring = isSteelheadLicenseExpiring();

        if (steelheadLicenseExpiring != oldSteelheadLicenseExpiring) {
            steelheadFlags ^= STEELHEAD_LICENSE_EXPIRING;
            firePropertyChange(Prop.steelheadLicenseExpiring, oldSteelheadLicenseExpiring, steelheadLicenseExpiring);
        }
    }

    public boolean isSteelheadLicenseExpired() {
        return isFlagSet(steelheadFlags, STEELHEAD_LICENSE_EXPIRED);
    }

    public void setSteelheadLicenseExpired(boolean steelheadLicenseExpired) {
        boolean oldSteelheadLicenseExpired = isSteelheadLicenseExpired();

        if (steelheadLicenseExpired != oldSteelheadLicenseExpired) {
            steelheadFlags ^= STEELHEAD_LICENSE_EXPIRED;
            firePropertyChange(Prop.steelheadLicenseExpired, oldSteelheadLicenseExpired, steelheadLicenseExpired);
        }
    }

    /**
     * Returns a copy of the {@link Set} that holds this device's
     * {@link DeviceResource}s.
     * 
     * @return the {@link DeviceResource}s.
     */
    public List<DeviceResource> getDeviceResources() {
        return new ArrayList<DeviceResource>(deviceResources);
    }

    /**
     * Adds new {@link DeviceResource}s to this device.
     * 
     * @param deviceResources
     *            the {@link DeviceResource}s.
     */
    public void addDeviceResources(List<DeviceResource> deviceResources) {
        if (deviceResources != null && !deviceResources.isEmpty()) {
            for (DeviceResource deviceResource : deviceResources) {
                deviceResource.setDevice(this);
                this.deviceResources.add(deviceResource);
            }

            firePropertyChange(Prop.addDeviceResources, null, deviceResources);
        }
    }

    /**
     * Removes {@link DeviceResource}s from this device.
     * 
     * @param deviceResources
     *            the {@link DeviceResource}s.
     */
    public void removeDeviceResources(List<DeviceResource> deviceResources) {
        if (deviceResources != null && !deviceResources.isEmpty()) {
            for (DeviceResource deviceResource : deviceResources) {
                this.deviceResources.remove(deviceResource);
                deviceResource.setDevice(null);
            }

            firePropertyChange(Prop.removeDeviceResources, null, deviceResources);
        }
    }

    public Interface getLinkInterface() {
        for (Interface i : interfaces) {
            if (i.isAvailability()) {
                return i;
            }
        }

        return null;
    }

    /**
     * Returns a copy of the {@link List} that holds this {@link Device}'s
     * properties.
     * 
     * @return the properties.
     */
    public Map<String, Property> getProperties() {
        return new HashMap<String, Property>(properties);
    }

    /**
     * Adds new properties to this {@link Device}.
     * 
     * @param properties
     *            the properties.
     */
    public void addProperties(Map<String, Property> properties) {
        if (properties != null && !properties.isEmpty()) {
            this.properties.putAll(properties);
            firePropertyChange(Prop.addProperties, null, properties);
        }
    }

    /**
     * Removes properties from this {@link Device}.
     * 
     * @param propperties
     *            the properties.
     */
    public void removeProperties(Map<String, Property> properties) {
        if (properties != null && !properties.isEmpty()) {
            for (String name : properties.keySet()) {
                this.properties.remove(name);
            }
            firePropertyChange(Prop.removeProperties, null, properties);
        }
    }

    /**
     * Returns the {@link Property} with the specified name.
     * 
     * @param name
     *            the {@link Property}'s name.
     * @return the matched {@link Property}.
     */
    public Property getProperty(String name) {
        return properties.get(name);
    }

    private boolean isFlagSet(int flags, int bit) {
        return (flags & bit) == bit;
    }

    @Override
    public String toString() {
        return ip;
    }

    // TODO find a better way to compare devices
    @Override
    public int compareTo(Device o) {
        return getIp().compareTo(o.getIp());
    }

    @Override
    public NmsProperty getNmsProperty(String name) {
        return Prop.valueOf(name);
    }

    @Override
    public NmsProperty[] getNmsProperties() {
        return Prop.values();
    }

    public enum Type {
        UNKNOWN("Indefinido", "ethernet", true), // for compatibility
        GATEKEEPER("Gatekeeper"),
        GATEWAY("Gateway"),
        MCU("MCU"),
        PBX("PBX"),
        ROUTER("Router"),
        SWITCHBOARD("Conmutador"),
        SERVER("Servidor"),
        SWITCH("Switch"), // *
        UPS("UPS"),
        VIDEO("Video"),
        WIRELESS("Wireless"),
        IPS("IPS"),
        SHAPER("Modelador"),
        VOICE("Voz"),
        OMNISWITCH("Omniswitch"),
        FIREWALL("Firewall"),
        TELECONTROLLER("Telecontroller", false),
        PC("PC"),
        PIPELINE("Tuber\u00EDa", "telecontroller", false),
        TELECONTROLLER_T1_100("ImageStream RC", "telecontroller", false);

        private String name;
        private String iconName;
        private boolean hasIp;

        private Type(String name) {
            this(name, true);
        }

        private Type(String name, boolean hasIp) {
            init(name, name().toLowerCase(), hasIp);
        }

        private Type(String name, String iconName, boolean hasIp) {
            init(name, iconName, hasIp);
        }

        private void init(String name, String iconName, boolean hasIp) {
            this.name = name;
            this.iconName = iconName;
            this.hasIp = hasIp;
        }

        public String getName() {
            return name;
        }

        public String getIconName() {
            return iconName;
        }

        public boolean hasIp() {
            return hasIp;
        }

        public static boolean isTelecontroller(Type type) {
            return type == TELECONTROLLER || type == TELECONTROLLER_T1_100;
        }
    }

    public enum CliProtocol {
        TELNET("telnet"),
        SSH("ssh");

        private String protocolType;

        private CliProtocol(String protocolType) {
            this.protocolType = protocolType;
        }

        public String getVersion() {
            return protocolType;
        }
    }

    public enum Prop implements NmsProperty, AlertAttribute, CompareProjectInterface {
        projectId(long.class),
        sysObjectID(String.class),
        type(Type.class),
        sysUpTime(long.class),
        reachable(boolean.class),
        managed(boolean.class),
        pingable(boolean.class),
        snmpable(boolean.class),
        trackingStats(boolean.class),
        testTimeout(int.class),
        intensiveTestDuration(int.class),
        ip(String.class),
        hostname(String.class),
        alias(String.class),
        description(String.class),
        contact(String.class),
        location(String.class),
        snmpPublicCommunity(String.class),
        snmpTimeout(int.class),
        snmpRetries(int.class),
        maxGetSize(int.class),
        lastUpdateTime(long.class),
        // graphAttributes(JGraphAttributes.class, true),

        deviceDownAlert(boolean.class, Alert.Type.DeviceDown),
        warning(boolean.class, Alert.Type.DeviceChanged),
        snmpUnreachable(boolean.class, Alert.Type.SNMPTimeOut),
        reachableByRedundantNms(boolean.class, Alert.Type.RedundantReachable),
        upsRemainingCharge(boolean.class, Alert.Type.UPSEstimatedChargeRemaining),
        upsBatteryStatus(boolean.class, Alert.Type.UPSBatteryStatus),
        upsOnBattery(boolean.class, Alert.Type.UPSSecondsOnBattery),
        deviceConfChanged(boolean.class, Alert.Type.DeviceConfChanged),

        syslogSeverity0(boolean.class, Alert.Type.Syslog0),
        syslogSeverity1(boolean.class, Alert.Type.Syslog1),
        syslogSeverity2(boolean.class, Alert.Type.Syslog2),
        syslogSeverity3(boolean.class, Alert.Type.Syslog3),
        syslogSeverity4(boolean.class, Alert.Type.Syslog4),
        syslogSeverity5(boolean.class, Alert.Type.Syslog5),
        syslogSeverity6(boolean.class, Alert.Type.Syslog6),
        syslogSeverity7(boolean.class, Alert.Type.Syslog7),

        snmpVersion(SnmpVersion.class),
        snmpUser(String.class),
        securityLevel(int.class),
        authenticationProtocol(String.class),
        latencyMeasurementIp(String.class),
        statsTableId(String.class),
        telnetUser(String.class),
        telnetPassword(String.class),
        telnetable(boolean.class),
        privilegeModePassword(String.class),

        inventory(boolean.class),
        connectionProtocolType(CliProtocol.class),

        /* Riverbed Steelhead */

        steelheadSystemHealth(boolean.class, Alert.Type.STEELHEAD_SYSTEM_HEALTH),
        steelheadOptServiceStatus(boolean.class, Alert.Type.STEELHEAD_OPT_SERVICE_STATUS),
        steelheadCpuUtil(boolean.class, Alert.Type.STEELHEAD_CPU_UTIL),
        steelheadBypassMode(boolean.class, Alert.Type.STEELHEAD_BYPASS_MODE),
        steelheadAdmissionMemError(boolean.class, Alert.Type.STEELHEAD_ADMISSION_MEM_ERROR),
        steelheadAdmissionConnError(boolean.class, Alert.Type.STEELHEAD_ADMISSION_CONN_ERROR),
        steelheadHaltError(boolean.class, Alert.Type.STEELHEAD_HALT_ERROR),
        steelheadServiceError(boolean.class, Alert.Type.STEELHEAD_SERVICE_ERROR),
        steelheadLinkError(boolean.class, Alert.Type.STEELHEAD_LINK_ERROR),
        steelheadAsymRouteError(boolean.class, Alert.Type.STEELHEAD_ASYM_ROUTE_ERROR),
        steelheadTemperatureWarning(boolean.class, Alert.Type.STEELHEAD_TEMPERATURE_WARNING),
        steelheadTemperatureCritical(boolean.class, Alert.Type.STEELHEAD_TEMPERATURE_CRITICAL),
        steelheadDiskError(boolean.class, Alert.Type.STEELHEAD_DISK_ERROR),
        steelheadAdmissionCpuError(boolean.class, Alert.Type.STEELHEAD_ADMISSION_CPU_ERROR),
        steelheadAdmissionTcpError(boolean.class, Alert.Type.STEELHEAD_ADMISSION_TCP_ERROR),
        steelheadSystemDiskFullError(boolean.class, Alert.Type.STEELHEAD_SYSTEM_DISK_FULL_ERROR),
        steelheadCertsExpiringError(boolean.class, Alert.Type.STEELHEAD_CERTS_EXPIRING_ERROR),
        steelheadLicenseError(boolean.class, Alert.Type.STEELHEAD_LICENSE_ERROR),
        steelheadHardwareError(boolean.class, Alert.Type.STEELHEAD_HARDWARE_ERROR),
        steelheadLanWanLoopError(boolean.class, Alert.Type.STEELHEAD_LAN_WAN_LOOP_ERROR),
        steelheadOptimizationServiceStatusError(boolean.class, Alert.Type.STEELHEAD_OPTIMIZATION_SERVICE_STATUS_ERROR),
        steelheadLicenseExpiring(boolean.class, Alert.Type.STEELHEAD_LICENSE_EXPIRING),
        steelheadLicenseExpired(boolean.class, Alert.Type.STEELHEAD_LICENSE_EXPIRED),

        // interfaces
        interfaces(Set.class, true),
        addInterfaces(Set.class, true),
        removeInterfaces(Set.class, true),

        // links
        links(Set.class, true),
        addLinks(Set.class, true),
        removeLinks(Set.class, true),

        // IP SLAs
        ipSlas(Set.class, true),
        addIpSlas(Set.class, true),
        removeIpSlas(Set.class, true),

        // DeviceResources
        deviceResources(List.class, true),
        addDeviceResources(List.class, true),
        removeDeviceResources(List.class, true),

        // properties
        properties(Map.class, true),
        addProperties(Map.class, true),
        removeProperties(Map.class, true),

        sharingPingConfiguration(boolean.class),
        sharingSnmpConfiguration(boolean.class),
        sharingCliConfiguration(boolean.class),
        sharingTrapsConfiguration(boolean.class),
        syslog0Enabled(boolean.class),
        syslog1Enabled(boolean.class),
        syslog2Enabled(boolean.class),
        syslog3Enabled(boolean.class),
        syslog4Enabled(boolean.class),
        syslog5Enabled(boolean.class),
        syslog6Enabled(boolean.class),
        syslog7Enabled(boolean.class),
        trapsEnabled(boolean.class);

        private Class<?> _type;
        private Method getter;
        private Method updater;
        private boolean entity;
        private Alert.Type alertType;

        private Prop(Class<?> type, boolean entity) {
            this(type, entity, null);

        }

        private Prop(Class<?> type, boolean entity, Alert.Type alertType) {
            this._type = type;
            this.entity = entity;
            this.alertType = alertType;
            getter = ClassUtil.getGetter(Device.class, name());
            updater = ClassUtil.getUpdater(Device.class, name(), type);
        }

        private Prop(Class<?> type, Alert.Type alertTyep) {
            this(type, false, alertTyep);
        }

        private Prop(Class<?> type) {
            this(type, false);
        }

        @Override
        public Class<?> getType() {
            return _type;
        }

        @Override
        public Method getGetter() {
            return getter;
        }

        @Override
        public Method getUpdater() {
            return updater;
        }

        @Override
        public boolean isEntity() {
            return entity;
        }

        @Override
        public Alert.Type getAlertType() {
            return alertType;
        }

        @Override
        public boolean isUsedOnCompareProject(NmsEntity entity) {
            if (getAlertType() != null || getGetter() == null) {
                return false;
            }
            switch (this) {
                case projectId:
                case sysUpTime:
                case description:
                case contact:
                case location:
                case statsTableId:
                    // case graphAttributes:
                case links:
                case lastUpdateTime:
                    return false;
                default:
                    return true;
            }
        }

    }

}
