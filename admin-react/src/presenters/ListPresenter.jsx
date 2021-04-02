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
import config from "%/config";

export default () => {
    const context = useContext(ListContext);
    const sort = context.query.sort || config.defaultSort;

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
                sortKey={sort[0]}
                sortOrder={sort[1]}
                defaultSortKey="created_at"
                defaultSortOrder="DESC"
                onSort={e => context.setSort([e.key, e.sortOrder])}
            />
            <Pagination />
        </FullwidthLayout>
    );
};
