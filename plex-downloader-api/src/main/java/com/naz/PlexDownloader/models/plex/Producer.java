package com.naz.PlexDownloader.models.plex;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
public class Producer {

    @Id
    @XmlAttribute(name = "id")
    private Long producerId;
    @XmlAttribute
    private String tag;

    @XmlAttribute
    private String filter;

    public Long getProducerId() {
        return producerId;
    }

    public void setProducerId(Long id) {
        this.producerId = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }
}
