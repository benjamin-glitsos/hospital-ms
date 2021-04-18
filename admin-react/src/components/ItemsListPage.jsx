import R from "ramda";
import { titleCase } from "title-case";
import PageContainer from "%/components/Page/PageContainer";
import ListContainer from "%/components/List/ListContainer";
import List from "%/components/List/List";
import config from "%/config";

export default () => {
    const [nameSingular, namePlural] = config.names.items;
    const keyColumnSingular = "key";
    const keyColumnPlural = "keys";
    const title = titleCase(namePlural);
    const slug = namePlural;

    const pageContainer = PageContainer({
        nameSingular,
        namePlural,
        title,
        slug,
        namePlural,
        description: `A list of ${namePlural} who can log into the ${
            process.env.PROJECT_NAME || "Item Management System"
        }.`
    });

    const listContainer = ListContainer({
        defaultState: {
            request: {
                body: {}
            },
            response: {
                data: {
                    items: []
                },
                errors: []
            },
            isLoading: false,
            selected: []
        },
        headContentColumns: [
            { key: "key", content: "Key", isSortable: true },
            { key: "name", content: "Name", isSortable: true },
            {
                key: "description",
                content: "Description",
                isSortable: true
            }
        ],
        nameSingular,
        namePlural,
        keyColumnSingular,
        keyColumnPlural,
        rowTransform: R.identity
    });

    const pageContext = {
        ...pageContainer,
        ...listContainer
    };

    return <List context={pageContext} />;
};
