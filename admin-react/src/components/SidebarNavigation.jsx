import {
    SideNavigation,
    NavigationContent,
    NavigationHeader,
    Header,
    LinkItem,
    Section
} from "@atlaskit/side-navigation";
import logo from "../assets/logo.svg";
import styled from "styled-components";
import { matchPath } from "react-router";
import { useLocation } from "react-router-dom";
import MediaServicesDocumentIcon from "@atlaskit/icon/glyph/media-services/document";
import PeopleIcon from "@atlaskit/icon/glyph/people";
import UserAvatarCircleIcon from "@atlaskit/icon/glyph/user-avatar-circle";

export default () => {
    const location = useLocation();

    const HeaderLink = ({ children, ...props }) => {
        return (
            <LinkItem href="/" {...props}>
                {children}
            </LinkItem>
        );
    };

    const mode = process.env.REACT_APP_PROJECT_MODE || process.env.NODE_ENV;

    const modeIndicator = mode === "production" ? "" : ` (${mode})`;

    const doesMatchLocation = path =>
        matchPath(location.pathname, { path, exact: true });

    return (
        <SideNavigation label="Main Navigation">
            <NavigationContent>
                <NavigationHeader>
                    <Header
                        component={HeaderLink}
                        iconBefore={
                            <AppLogo
                                src={logo}
                                alt={`${
                                    process.env.REACT_APP_PROJECT_NAME ||
                                    "Item Management System"
                                } logo`}
                            />
                        }
                        description={
                            process.env.REACT_APP_PROJECT_NAME ||
                            "Item Management System"
                        }
                    >
                        {(process.env.REACT_APP_PROJECT_ABBREV || "IMS") +
                            modeIndicator}
                    </Header>
                </NavigationHeader>
                <Section key="SideNavigation/Section/Home" title="Home">
                    <LinkItem
                        key="SideNavigation/LinkItem/Readme"
                        iconBefore={<MediaServicesDocumentIcon size="medium" />}
                        href="/"
                        isSelected={doesMatchLocation("/")}
                    >
                        Readme
                    </LinkItem>
                </Section>
                <Section key="SideNavigation/Section/System" title="System">
                    <LinkItem
                        key="SideNavigation/LinkItem/Users"
                        iconBefore={<PeopleIcon size="medium" />}
                        href="/users"
                        isSelected={doesMatchLocation("/users")}
                    >
                        Users
                    </LinkItem>
                </Section>
                <Section
                    key="SideNavigation/Section/YourProfile"
                    title="Your Profile"
                >
                    <LinkItem
                        key="SideNavigation/LinkItem/YourProfile"
                        iconBefore={<UserAvatarCircleIcon size="medium" />}
                        href="/users/username"
                        isSelected={doesMatchLocation("/users/username")}
                        description="You are logged in as username."
                    >
                        Firstname Lastname
                    </LinkItem>
                </Section>
            </NavigationContent>
        </SideNavigation>
    );
};

const AppLogo = styled.img`
    width: 100%;
`;
