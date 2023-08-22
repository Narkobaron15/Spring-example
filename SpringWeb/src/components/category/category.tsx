import ICategoryItem from "../../models/category";

type CategoryArgs = {
    category: ICategoryItem,
};

export default function Category({ category }: CategoryArgs) {
    return (
        <tr key={category.id} className="border-t">
            <td>
                <img src={category.imageURL} alt={category.name} />
            </td>
            <th scope='row'>{category.id}</th>
            <td>{category.name}</td>
            <td>{category.description}</td>
        </tr>
    );
}