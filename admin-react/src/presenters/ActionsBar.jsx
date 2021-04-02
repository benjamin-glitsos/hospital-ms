import { useContext, useEffect, useState } from "react";
import DeletionMenu from "%/presenters/DeletionMenu";
import PageLengthSelect from "%/presenters/PageLengthSelect";
import ButtonGroup from "@atlaskit/button/button-group";
import Textfield from "@atlaskit/textfield";
import { ListContext } from "%/components/List";

export default () => {
    const context = useContext(ListContext);
    const isDataEmpty = context.isDataEmpty;
    const isDeletable = isDataEmpty || context.state.selected.length > 0;
    const softDeleteAction = () =>
        context.deleteItemsAction("soft", context.state.selected);
    const hardDeleteAction = () =>
        context.deleteItemsAction("hard", context.state.selected);
    const pageLength = context.state.response.data.page_length;
    const totalPages = context.state.response.data.total_pages;
    const setPageLength = context.setPageLength;
    const setSearch = context.setSearch;
    const search = context.query.search;

    const [searchState, setSearchState] = useState("");

    useEffect(() => setSearchState(search), []);

    return (
        <ButtonGroup>
            <DeletionMenu
                isDisabled={isDeletable}
                softDeleteAction={softDeleteAction}
                hardDeleteAction={hardDeleteAction}
            />
            <PageLengthSelect
                isDisabled={isDataEmpty}
                pageLength={pageLength}
                setPageLength={setPageLength}
            />
        </ButtonGroup>
    );
};
// <Textfield
//     isDisabled={isDataEmpty}
//     aria-label="Search"
//     isCompact={true}
//     placeholder="Search"
//     value={searchState}
//     onChange={search => {
//         const value = search.target.value;
//         setSearch(value);
//         setSearchState(value);
//     }}
// />
