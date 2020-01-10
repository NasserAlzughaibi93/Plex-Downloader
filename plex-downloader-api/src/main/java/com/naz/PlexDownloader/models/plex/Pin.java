package com.naz.PlexDownloader.models.plex;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

//TODO Pin Request for OAuth: https://plex.tv/api/v2/pins?strong=true&X-Plex-Product=NAZ&X-Plex-Client-Identifier=NAZMB&X-Plex-Version=1.0.0
//TODO OAuth example:  https://app.plex.tv/auth#?forwardUrl=http://google.com&clientID={PIN_ID}&code={CODE}&X-Plex-Product=NAZ&X-Plex-Client-Identifier=NAZMB&X-Plex-Version=1.0.0
//TODO Auth Token GET: https://plex.tv/api/v2/pins/{PIN_ID}
@XmlAccessorType(XmlAccessType.FIELD)
public class Pin implements Serializable {

    @XmlAttribute
    private String expiresIn;

    @XmlAttribute
    private String createdAt;

    @XmlAttribute
    private String product;

    @XmlAttribute
    private String code;

    @XmlAttribute
    private String trusted;

    @XmlAttribute
    private String clientIdentifier;

    @XmlAttribute
    private String authToken;

    @XmlElement
    private Location location;

    @XmlAttribute
    private String id;

    @XmlAttribute
    private String expiresAt;

    private transient String resolvedUri;

    public String getResolvedUri() {
        return resolvedUri;
    }

    public void setResolvedUri(String resolvedUri) {
        this.resolvedUri = resolvedUri;
    }

    public String getExpiresIn ()
    {
        return expiresIn;
    }

    public void setExpiresIn (String expiresIn)
    {
        this.expiresIn = expiresIn;
    }

    public String getCreatedAt ()
    {
        return createdAt;
    }

    public void setCreatedAt (String createdAt)
    {
        this.createdAt = createdAt;
    }

    public String getProduct ()
    {
        return product;
    }

    public void setProduct (String product)
    {
        this.product = product;
    }

    public String getCode ()
    {
        return code;
    }

    public void setCode (String code)
    {
        this.code = code;
    }

    public String getTrusted ()
    {
        return trusted;
    }

    public void setTrusted (String trusted)
    {
        this.trusted = trusted;
    }

    public String getClientIdentifier ()
    {
        return clientIdentifier;
    }

    public void setClientIdentifier (String clientIdentifier)
    {
        this.clientIdentifier = clientIdentifier;
    }

    public String getAuthToken ()
    {
        return authToken;
    }

    public void setAuthToken (String authToken)
    {
        this.authToken = authToken;
    }

    public Location getLocation ()
    {
        return location;
    }

    public void setLocation (Location location)
    {
        this.location = location;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getExpiresAt ()
    {
        return expiresAt;
    }

    public void setExpiresAt (String expiresAt)
    {
        this.expiresAt = expiresAt;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [expiresIn = "+expiresIn+", createdAt = "+createdAt+", product = "+product+", code = "+code+", trusted = "+trusted+", clientIdentifier = "+clientIdentifier+", authToken = "+authToken+", location = "+location+", id = "+id+", expiresAt = "+expiresAt+"]";
    }
}