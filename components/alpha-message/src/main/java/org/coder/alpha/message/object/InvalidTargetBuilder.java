package org.coder.alpha.message.object;

/**
 * InvalidTargetBuilder.
 *
 * @author yoshida-n
 * @version	created.
 */
public class InvalidTargetBuilder {

    /**
     *  the component id.
     */
    private String componentId = null;

    /**
     * the row number.
     */
    private int rowNum = -1;

    /**
     * the containerId
     */
    private String containerId = null;

    /**
     * <pre>
     *    コンストラクタ .
     * </pre>
     * 
     * @param componentId
     *            to set
     */
    public InvalidTargetBuilder(String componentId) {
        this.componentId = componentId;
    }

    /**
     * <pre>
     *    for container .
     * </pre>
     * 
     * @param rowNum
     *            to set
     * @param tableId
     *            to set
     * @return self
     */
    public InvalidTargetBuilder withRowNum(int rowNum, String containerId) {
        this.rowNum = rowNum;
        this.containerId =containerId;
        return this;
    }

    /**
     * <pre>
     *   Builds the target .
     * </pre>
     * 
     * @return the target
     */
    public InvalidTarget build() {
        InvalidTarget target = new InvalidTarget();
        target.setComponentId(componentId);
        target.setRowNum(rowNum);
        target.setContainerId(containerId);
        return target;
    }

}