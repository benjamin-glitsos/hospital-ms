import { useContext } from "react";
import DynamicTable from "@atlaskit/dynamic-table";
import PageHeader from "@atlaskit/page-header";
import FullwidthLayout from "%/presenters/FullwidthLayout";
import BreadcrumbBar from "%/presenters/BreadcrumbBar";
import TableStatusBar from "%/presenters/TableStatusBar";
import NoData from "%/presenters/NoData";
import ActionsBar from "%/presenters/ActionsBar";
import Pagination from "%/presenters/Pagination";
import { ListContext } from "%/components/List";

export default () => {
    const context = useContext(ListContext);

    return (
        <FullwidthLayout>
            <PageHeader
                breadcrumbs={
                    <BreadcrumbBar breadcrumbs={[context.homeBreadcrumb]} />
                }
                actions={<ActionsBar />}
                bottomBar={<TableStatusBar />}
            >
                {context.title}
            </PageHeader>
            <DynamicTable
                head={context.head}
                rows={context.rows}
                isLoading={context.state.isLoading}
                emptyView={<NoData />}
                defaultSortKey="created_at"
                defaultSortOrder="DESC"
                sortKey={context.query.sort[0]}
                sortOrder={context.query.sort[1]}
                onSort={e => context.setSort([e.key, e.sortOrder])}
            />
            <Pagination />
        </FullwidthLayout>
    );
};
