package com.naz.PlexDownloader.models.plex;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deviceId;
    @XmlAttribute
    private String name;
    @XmlAttribute
    private String product;
    @XmlAttribute
    private String productVersion;
    @XmlAttribute
    private String platform;
    @XmlAttribute
    private String platformVersion;
    @XmlAttribute(name = "device")
    private String device;
    @XmlAttribute
    private String clientIdentifier;
    @XmlAttribute
    private String createdAt;
    @XmlAttribute
    private String lastSeenAt;
    @XmlAttribute
    private String provides;
    @XmlAttribute
    private String owned;
    @XmlAttribute
    private String publicAddress;
    @XmlAttribute
    private String httpsRequired;
    @XmlAttribute
    private String synced;
    @XmlAttribute
    private String relay;
    @XmlAttribute
    private String dnsRebindingProtection;
    @XmlAttribute
    private String natLoopbackSupported;
    @XmlAttribute
    private String publicAddressMatches;
    @XmlAttribute
    private String presence;

    @XmlElement(name = "Connection")
    @OneToMany(cascade = CascadeType.ALL)
    private List<Connection> connection;
    @XmlAttribute
    private String ownerID;
    @XmlAttribute
    private String home;
    @XmlAttribute
    private String accessToken;
    @XmlAttribute
    private String sourceTitle;

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getProductVersion() {
        return productVersion;
    }

    public void setProductVersion(String productVersion) {
        this.productVersion = productVersion;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getPlatformVersion() {
        return platformVersion;
    }

    public void setPlatformVersion(String platformVersion) {
        this.platformVersion = platformVersion;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getClientIdentifier() {
        return clientIdentifier;
    }

    public void setClientIdentifier(String clientIdentifier) {
        this.clientIdentifier = clientIdentifier;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getLastSeenAt() {
        return lastSeenAt;
    }

    public void setLastSeenAt(String lastSeenAt) {
        this.lastSeenAt = lastSeenAt;
    }

    public String getProvides() {
        return provides;
    }

    public void setProvides(String provides) {
        this.provides = provides;
    }

    public String getOwned() {
        return owned;
    }

    public void setOwned(String owned) {
        this.owned = owned;
    }

    public String getPublicAddress() {
        return publicAddress;
    }

    public void setPublicAddress(String publicAddress) {
        this.publicAddress = publicAddress;
    }

    public String getHttpsRequired() {
        return httpsRequired;
    }

    public void setHttpsRequired(String httpsRequired) {
        this.httpsRequired = httpsRequired;
    }

    public String getSynced() {
        return synced;
    }

    public void setSynced(String synced) {
        this.synced = synced;
    }

    public String getRelay() {
        return relay;
    }

    public void setRelay(String relay) {
        this.relay = relay;
    }

    public String getDnsRebindingProtection() {
        return dnsRebindingProtection;
    }

    public void setDnsRebindingProtection(String dnsRebindingProtection) {
        this.dnsRebindingProtection = dnsRebindingProtection;
    }

    public String getNatLoopbackSupported() {
        return natLoopbackSupported;
    }

    public void setNatLoopbackSupported(String natLoopbackSupported) {
        this.natLoopbackSupported = natLoopbackSupported;
    }

    public String getPublicAddressMatches() {
        return publicAddressMatches;
    }

    public void setPublicAddressMatches(String publicAddressMatches) {
        this.publicAddressMatches = publicAddressMatches;
    }

    public String getPresence() {
        return presence;
    }

    public void setPresence(String presence) {
        this.presence = presence;
    }

    public List<Connection> getConnection() {
        return connection;
    }

    public void setConnection(List<Connection> connection) {
        this.connection = connection;
    }

    public String getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(String ownerID) {
        this.ownerID = ownerID;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getSourceTitle() {
        return sourceTitle;
    }

    public void setSourceTitle(String sourceTitle) {
        this.sourceTitle = sourceTitle;
    }
}
