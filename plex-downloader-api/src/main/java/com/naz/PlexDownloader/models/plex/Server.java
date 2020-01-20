package com.naz.PlexDownloader.models.plex;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

//@Entity
@XmlAccessorType(XmlAccessType.FIELD)
public class Server {

    @Id
    @XmlAttribute
    private Long id;

    @XmlAttribute
    private String lastSeenAt;

    @XmlAttribute
    private String numLibraries;

    @XmlAttribute
    private String allLibraries;

    @XmlAttribute
    private String machineIdentifier;

    @XmlAttribute
    private String owned;

    @XmlAttribute
    private String pending;

    @XmlAttribute
    private String name;

    @XmlAttribute
    private String serverId;

    public String getLastSeenAt ()
    {
        return lastSeenAt;
    }

    public void setLastSeenAt (String lastSeenAt)
    {
        this.lastSeenAt = lastSeenAt;
    }

    public String getNumLibraries ()
    {
        return numLibraries;
    }

    public void setNumLibraries (String numLibraries)
    {
        this.numLibraries = numLibraries;
    }

    public String getAllLibraries ()
    {
        return allLibraries;
    }

    public void setAllLibraries (String allLibraries)
    {
        this.allLibraries = allLibraries;
    }

    public String getMachineIdentifier ()
    {
        return machineIdentifier;
    }

    public void setMachineIdentifier (String machineIdentifier)
    {
        this.machineIdentifier = machineIdentifier;
    }

    public String getOwned ()
    {
        return owned;
    }

    public void setOwned (String owned)
    {
        this.owned = owned;
    }

    public String getPending ()
    {
        return pending;
    }

    public void setPending (String pending)
    {
        this.pending = pending;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getServerId ()
    {
        return serverId;
    }

    public void setServerId (String serverId)
    {
        this.serverId = serverId;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [lastSeenAt = "+lastSeenAt+", numLibraries = "+numLibraries+", allLibraries = "+allLibraries+", machineIdentifier = "+machineIdentifier+", owned = "+owned+", pending = "+pending+", name = "+name+", id = "+id+", serverId = "+serverId+"]";
    }
}
