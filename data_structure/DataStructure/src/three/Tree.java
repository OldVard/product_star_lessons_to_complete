package three;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class Tree {
    public static void main(String[] args) {
        TreeNode rootNode = new TreeNode("html");
        TreeNode headNode = new TreeNode("head");
        TreeNode bodyNode = new TreeNode("body");
        TreeNode titleNode = new TreeNode("title");
        TreeNode divNode = new TreeNode("div");
        TreeNode pNode = new TreeNode("p");
        TreeNode h1Node = new TreeNode("h1");

        rootNode.addChild(headNode);
        rootNode.addChild(bodyNode);
        headNode.addChild(titleNode);
        bodyNode.addChild(divNode);
        bodyNode.addChild(h1Node);
        divNode.addChild(pNode);

        Tree tree = new Tree(rootNode);
        System.out.println(tree);
        System.out.println("=============================");

        TreeNode foundNode = tree.findNodeByValue("body");
        System.out.println(foundNode);
        System.out.println("=============================");

        boolean result = tree.deleteNode("div");
        System.out.println("Удалено? " + result);
        System.out.println(tree);
    }

    TreeNode root;

    public Tree(TreeNode rootNode) {
        this.root = rootNode;
    }

    @Override
    public String toString() {
        return root.toString();
    }

    // Начинает поиск с корневого узла дерева.
    // Если root.value совпадает с искомым — возвращает его.
    // Если не совпадает — перебирает всех детей и вызывает второй метод findNodeByValue(child, value) рекурсивно.
    // Если ни один потомок не подошёл — возвращает null.
    public TreeNode findNodeByValue(String value) {
        TreeNode currentNode = root;
        if(!currentNode.getValue().equals(value)) {
            for(TreeNode child : currentNode.getChildren()) {
                TreeNode nodeByValue = findNodeByValue(child, value);
                if(nodeByValue != null) {
                    return nodeByValue;
                }
            }
            return null;
        }
        return currentNode;
    }

    // Это рекурсивный помощник, он:
    //Проверяет: текущий узел совпадает по значению?
    //Да — возвращаем его.
    //Нет — идём вглубь, перебираем детей.
    //Если ни один из детей не подходит — возвращаем null.
    public TreeNode findNodeByValue(TreeNode currentNode, String value) {
        if(!currentNode.getValue().equals(value)) {
            if(currentNode.getChildren() == null || currentNode.getChildren().isEmpty()) {
                return null;
            }
            for(TreeNode child : currentNode.getChildren()) {
                TreeNode nodeByValue = findNodeByValue(child, value);
                if(nodeByValue != null) {
                    return nodeByValue;
                }
            }
            return null;
        }
        return currentNode;
    }

    // Метод начинает удаление с корневого узла root.
    //Он проверяет, есть ли у него дети.
    //Если есть — перебирает их через итератор.
    //Для каждого ребёнка вызывает второй метод deleteNode(child, value).
    //Если узел с нужным значением найден и возвращено true:
    //ещё раз проверяет: совпадает ли child.getValue() с тем, что ищем?
    //если да — удаляет его из списка детей у root.
    //Возвращает true, если хоть один узел был удалён, иначе — false.
    public boolean deleteNode(String value) {
        if(root.getChildren() == null || root.getChildren().isEmpty()) {
            return false;
        }
        Iterator<TreeNode> iterator = root.getChildren().iterator();
        while(iterator.hasNext()) {
            TreeNode child = iterator.next();
            boolean nodeByValue = deleteNode(child, value);
            if(nodeByValue) {
                if(child.getValue().equals(value)) {
                    root.getChildren().remove(child);
                }
                return true;
            }
        }
        return false;
    }

    // Рекурсивно ищет узел с нужным значением.
    //Если currentNode.value == value — возвращает true, сигнализируя, что найден.
    //Иначе идёт вглубь: перебирает детей и вызывает себя снова.
    //Если находит нужного ребёнка — удаляет его из списка currentNode.getChildren().
    public boolean deleteNode(TreeNode currentNode, String value) {
        if(!currentNode.getValue().equals(value)) {
            if(currentNode.getChildren() == null || currentNode.getChildren().isEmpty()) {
                return false;
            }
            Iterator<TreeNode> iterator = currentNode.getChildren().iterator();
            while(iterator.hasNext()) {
                TreeNode child = iterator.next();
                boolean nodeByValue = deleteNode(child, value);
                if(nodeByValue) {
                    if(child.getValue().equals(value)) {
                        currentNode.getChildren().remove(child);
                    }
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    // класс узла дерева
    public static class TreeNode {
        // каждый узел состоит из значения
        private String value;
        // а также может иметь потомков (детей)
        private List<TreeNode> children;

        // конструктор создает узел со значением и добавляет пустой список детей
        public TreeNode(String value) {
            this.value = value;
            this.children = new ArrayList<>();
        }

        public String getValue() {
            return value;
        }

        public List<TreeNode> getChildren() {
            return children;
        }

        // Каждый ребенок - узел
        public void addChild(TreeNode child) {
            children.add(child);
        }

        @Override
        public boolean equals(Object o) {
            return super.equals(o);
        }

        @Override
        public int hashCode() {
            return super.hashCode();
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            if(children != null && !children.isEmpty()) {
                for(TreeNode node : children) {
                    sb.append(node.toString());
                }
            }

            /*
            * toString() вызывается у объекта html
            * он обращается к своему value, т.е. "html"
            * потом вызывает toString() у своих детей (в данном случае — у body)
            * body.toString() тоже обращается к своему value, т.е. "body"
            * и так далее рекурсивно
            * */
            return "\n<" + value + ">" +
                    "\n" + sb +
                    "\n</" + value + ">";
        }
    }
}