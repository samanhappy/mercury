package com.dreamail.mercury.domain.qwert;

public class PushMail {
	/**
     * default serial version UID
     */
    private static final long serialVersionUID = 1L;
    //fields
    //PushMail version
    private Version version;
    private Cred cred;
    private Status status;
    private QwertTarget[] targets;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Version getVersion() {
        return version;
    }

    public void setVersion(Version version) {
        this.version = version;
    }

    public Cred getCred() {
        return cred;
    }

    public void setCred(Cred cred) {
        this.cred = cred;
    }

    public QwertTarget[] getTargets() {
        return targets;
    }

    public void setTargets(QwertTarget[] targets) {
        this.targets = targets;
    }
}
