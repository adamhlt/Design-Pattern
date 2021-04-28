package model;

/**
 * Store generation setting
 *
 * @version 1.0
 */
public class Setting {
    /**
     * Boolean : true if all identity need to be hide
     */
    private final boolean _withoutIdentity;

    /**
     * Boolean : true if all ID need to be hide
     */
    private final boolean _withoutID;

    /**
     * Boolean : true the planning need to be hide
     */
    private final boolean _withoutPlanning;

    /**
     * Generate setting object using parameters
     *
     * @param withoutIdentity true if all identity need to be hide
     * @param withoutID true if all ID need to be hide
     * @param withoutPlanning true the planning need to be hide
     */
    public Setting( boolean withoutIdentity, boolean withoutID, boolean withoutPlanning ) {
        this._withoutIdentity = withoutIdentity;
        this._withoutID = withoutID;
        this._withoutPlanning = withoutPlanning;
    }

    /**
     * Check if all identity need to be hide
     *
     * @return true if all identity need to be hide
     */
    public boolean isWithoutIdentity() { return _withoutIdentity; }

    /**
     * Check if all ID need to be hide
     *
     * @return true if all ID need to be hide
     */
    public boolean isWithoutID() { return _withoutID; }

    /**
     * Check if planning need to be hide
     *
     * @return true if planning need to be hide
     */
    public boolean isWithoutPlanning() { return _withoutPlanning; }
}
