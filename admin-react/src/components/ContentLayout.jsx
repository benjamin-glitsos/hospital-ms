import styled from "styled-components";
import PageHeader from "@atlaskit/page-header";
import BreadcrumbBar from "%/components/BreadcrumbBar";
import ContentMargins from "%/components/ContentMargins";

export default ({ title, breadcrumbs, maxWidth, children }) => {
    return (
        <MarginBottom>
            <ContentMargins maxWidth={maxWidth}>
                <PageHeader
                    breadcrumbs={<BreadcrumbBar breadcrumbs={breadcrumbs} />}
                >
                    {title}
                </PageHeader>
                {children}
            </ContentMargins>
        </MarginBottom>
    );
};

const MarginBottom = styled.div`
    margin-bottom: 28px;
`;
