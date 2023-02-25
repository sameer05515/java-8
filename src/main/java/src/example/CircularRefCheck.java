package example;

class ListNode {
    int val;
    ListNode next;

    ListNode(int x) {
        val = x;
        next = null;
    }
}

public class CircularRefCheck {
    public static boolean hasCycle(ListNode head) {
        // initialize two pointers, slow_ptr and fast_ptr, to the head of the linked list
        ListNode slow_ptr = head;
        ListNode fast_ptr = head;

        // traverse the linked list until the fast_ptr reaches the end (null) or encounters the slow_ptr
        while (fast_ptr != null && fast_ptr.next != null) {

            // move the slow_ptr one step at a time
            slow_ptr = slow_ptr.next;

            // move the fast_ptr two steps at a time
            fast_ptr = fast_ptr.next.next;

            // if the two pointers meet, the linked list is circular
            if (slow_ptr == fast_ptr) {
                return true;
            }
        }

        return false; // if the fast_ptr reaches the end, the linked list is not circular
    }

    public static void main(String[] args) {
        // create a circular linked list with five nodes
        ListNode head = new ListNode(26);
        ListNode node2 = new ListNode(74);
        ListNode node3 = new ListNode(89);
        ListNode node4 = new ListNode(43);
        ListNode node5 = new ListNode(54);

        head.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;

        // make the linked list circular by connecting the last node to the third node
        node5.next = node3;

        // check if the linked list is circular
        if (hasCycle(head)) {
            System.out.println("The linked list is circular.");
        } else {
            System.out.println("The linked list is not circular.");
        }
    }
}
