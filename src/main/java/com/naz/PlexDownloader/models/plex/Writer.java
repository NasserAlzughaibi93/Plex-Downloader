
package com.naz.PlexDownloader.models.plex;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
public class Writer {

    @XmlAttribute(name = "id")
    @Id
    private Integer writerId;
    @XmlAttribute(name = "filter")
    private String filter;
    @XmlAttribute(name = "tag")
    private String tag;

    public Integer getWriterId() {
        return writerId;
    }

    public void setWriterId(Integer id) {
        this.writerId = id;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
