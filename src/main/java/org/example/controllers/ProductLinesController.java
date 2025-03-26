package org.example.controllers;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import org.example.models.CategoryModel;
import org.example.models.ProductLinesModel;

import java.util.*;
import java.util.stream.Collectors;

public class ProductLinesController {

    private List<ProductLinesModel> productLinesList = new ArrayList<>();

    public List<ProductLinesModel> loadProductLines() {
        productLinesList.add(new ProductLinesModel("Cronos", "CronosOld", "Cronos 6001‑A"));
        productLinesList.add(new ProductLinesModel("Cronos", "CronosOld", "Cronos 6003"));
        productLinesList.add(new ProductLinesModel("Cronos", "CronosOld", "Cronos 7023"));

        productLinesList.add(new ProductLinesModel("Cronos", "Cronos L", "Cronos 6021L"));
        productLinesList.add(new ProductLinesModel("Cronos", "Cronos L", "Cronos 7023L"));

        productLinesList.add(new ProductLinesModel("Cronos", "Cronos‑NG", "Cronos 6001‑NG"));
        productLinesList.add(new ProductLinesModel("Cronos", "Cronos‑NG", "Cronos 6003‑NG"));
        productLinesList.add(new ProductLinesModel("Cronos", "Cronos‑NG", "Cronos 6021‑NG"));
        productLinesList.add(new ProductLinesModel("Cronos", "Cronos‑NG", "Cronos 6031‑NG"));
        productLinesList.add(new ProductLinesModel("Cronos", "Cronos‑NG", "Cronos 7021‑NG"));
        productLinesList.add(new ProductLinesModel("Cronos", "Cronos‑NG", "Cronos 7023‑NG"));

        productLinesList.add(new ProductLinesModel("Ares", "Ares TB", "ARES 7021"));
        productLinesList.add(new ProductLinesModel("Ares", "Ares TB", "ARES 7031"));
        productLinesList.add(new ProductLinesModel("Ares", "Ares TB", "ARES 7023"));

        productLinesList.add(new ProductLinesModel("Ares", " Ares THS", "ARES 8023 15"));
        productLinesList.add(new ProductLinesModel("Ares", " Ares THS", "ARES 8023 200"));
        productLinesList.add(new ProductLinesModel("Ares", " Ares THS", "ARES 8023 2,5"));

        return productLinesList;
    }

    public Set<String> getDistinctLinesSorted() {
        return productLinesList.stream()
                .map(ProductLinesModel::getLine)
                .filter(line -> !line.isEmpty())
                .distinct()
                .sorted()
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }


    // Método para filtrar categorias e modelos de uma linha selecionada e retornar como Set
    public Set<CategoryModel> getCategoriesAndModelsForLine(String selectedLine) {
        Set<CategoryModel> categoryModels = new HashSet<>();

        // Filtra as categorias e modelos para a linha selecionada
        productLinesList.stream()
                .filter(p -> p.getLine().equals(selectedLine))  // Filtra pela linha selecionada
                .forEach(productLine -> {
                    if (!productLine.getCategory().isEmpty() && !productLine.getModel().isEmpty()) {
                        categoryModels.add(new CategoryModel(productLine.getCategory(), productLine.getModel()));
                    }
                });

        return categoryModels;
    }

    // Método para carregar os dados no TreeView
    public void loadTreeViewData(TreeView<String> treeView, Set<CategoryModel> categoryModels) {

        // Cria um item raiz para a TreeView
        TreeItem<String> rootItem = new TreeItem<>("Categorias e Modelos");
        rootItem.setExpanded(true);

        // Agrupa as categorias
        Map<String, List<CategoryModel>> categoryMap = categoryModels.stream()
                .collect(Collectors.groupingBy(CategoryModel::getCategory));

        // Preenche a TreeView com categorias e modelos
        categoryMap.forEach((category, models) -> {
            TreeItem<String> categoryItem = new TreeItem<>(category);
            models.forEach(model -> {
                TreeItem<String> modelItem = new TreeItem<>(model.getModel());
                categoryItem.getChildren().add(modelItem);  // Adiciona o modelo à categoria
            });
            rootItem.getChildren().add(categoryItem);  // Adiciona a categoria à raiz
        });

        treeView.setRoot(rootItem);  // Configura a TreeView com a raiz
    }

}
