package de.frankbeeh.productbacklogtimeline.service.diff;

class DoubleLinkedBlocks {

    private final Block block;
    private DoubleLinkedBlocks next;
    private DoubleLinkedBlocks previous;

    public DoubleLinkedBlocks(Block block) {
        this.block = block;
    }

    public Block getBlock() {
        return block;
    }

    public DoubleLinkedBlocks getPrevious() {
        return previous;
    }

    public DoubleLinkedBlocks getNext() {
        return next;
    }

    public void setNext(DoubleLinkedBlocks next) {
        this.next = next;
    }

    public void setPrevious(DoubleLinkedBlocks previous) {
        this.previous = previous;
    }
}
