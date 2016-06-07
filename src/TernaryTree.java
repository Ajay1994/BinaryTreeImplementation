class TNode{
	char data;
	boolean isEnd;
	TNode left, right, middle;
	TNode(char data){
		this.data = data;
		this.isEnd = false;
		this.left = this.middle = this.right = null;
	}
}
class TNTree{
	TNode root = null;
	public TNode insert(String word, TNode root, int index){
		if(root == null){
			root = new TNode(word.charAt(index));
		}
		//if the word is smaller than the root word
		if(word.charAt(index) < root.data){
			root.left = insert(word, root.left, index);
		}
		if(word.charAt(index) > root.data){
			root.right = insert(word, root.right, index);
		}
		if(word.charAt(index) == root.data){
			if(index == word.length() - 1){
				root.isEnd = true;
			}else{
				root.middle = insert(word, root.middle, index + 1);
			}
		}
		return root;
	}
	
	public boolean search(String word, TNode root, int index){
		if(root == null){
			return false;
		}
		boolean isPresent = false;
		//if the word is smaller than the root word
		if(word.charAt(index) < root.data){
			isPresent = search(word, root.left, index);
		}
		if(word.charAt(index) > root.data){
			isPresent = search(word, root.right, index);
		}
		if(word.charAt(index) == root.data){
			if(index == word.length() - 1){
				return root.isEnd;
			}else{
				isPresent = search(word, root.middle, index + 1);
			}
		}
		return isPresent;
	}
	public void traverseTree(TNode root, String string){
		if(root == null) return;
		//left the left and right branch for expansion  
		traverseTree(root.left, string);
		traverseTree(root.right, string);
		//added node data to string and called upon middle
		string += root.data;
		if(root.isEnd == true){
			System.out.println(string);
		}
		traverseTree(root.middle, string);
		
	}
	public void delete(TNode root, String word, int index){
		if(root == null){
			return;
		}
		//if the word is smaller than the root word
		if(word.charAt(index) < root.data){
			delete(root.left, word, index);
		}
		if(word.charAt(index) > root.data){
			delete(root.right, word, index);
		}
		if(word.charAt(index) == root.data){
			if(index == word.length() - 1){
				//To delete just isEnd to be false
				root.isEnd = false;
			}else{
				delete(root.middle, word, index + 1);
			}
		}
		return;
	}
}
public class TernaryTree {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TNTree tree = new TNTree();
		tree.root = tree.insert("cat", tree.root, 0);
		tree.root = tree.insert("cats", tree.root, 0);
		tree.root = tree.insert("bug", tree.root, 0);
		tree.root = tree.insert("bulk", tree.root, 0);
		tree.root = tree.insert("amazon", tree.root, 0);
		tree.root = tree.insert("up", tree.root, 0);
		int i = 0;
		tree.traverseTree(tree.root, new String());
		
		System.out.println("Is Present : "+ tree.search("bullk", tree.root, 0));
		
		tree.delete(tree.root, "bulk", 0);
		
		tree.traverseTree(tree.root, new String());
	}

}
